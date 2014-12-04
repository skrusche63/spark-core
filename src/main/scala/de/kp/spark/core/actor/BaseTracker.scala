package de.kp.spark.core.actor
/* Copyright (c) 2014 Dr. Krusche & Partner PartG
 * 
 * This file is part of the Spark-Core project
 * (https://github.com/skrusche63/spark-core).
 * 
 * Spark-Core is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Spark-Core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * Spark-Core. 
 * 
 * If not, see <http://www.gnu.org/licenses/>.
 */

import de.kp.spark.core.Configuration

import de.kp.spark.core.Names
import de.kp.spark.core.model._

import de.kp.spark.core.io.ElasticWriter

abstract class BaseTracker(config:Configuration) extends RootActor(config) {
  
  def receive = {

    /*
     * A 'track' request generates two responses: an initial one, that informs
     * the sender that the data have been received and a second one that the
     * data have been processed.
     */
    case req:ServiceRequest => {
      /*
       * STEP#1: Prepare initial response 
       */
      val uid = req.data(Names.REQ_UID)
      
      val data = Map(Names.REQ_UID -> uid, "message" -> messages.TRACKED_DATA_RECEIVED(uid))
      val initialResponse = new ServiceResponse(req.service,req.task,data,status.SUCCESS)	
      
      val origin = sender
      origin ! initialResponse
      
      /*
       * STEP#2: Prepare final response 
       */
      val finalResponse = try {
        track(req)
      
      } catch {
        case e:Exception => failure(req,e.getMessage)
      }
      
      origin ! finalResponse
      context.stop(self)

    }
  
  }
  
  protected def track(req:ServiceRequest):ServiceResponse = {

   val uid = req.data(Names.REQ_UID)
   
   val index   = req.data(Names.REQ_INDEX)
   val mapping = req.data(Names.REQ_TYPES)
    
   val writer = new ElasticWriter()
        
   val readyToWrite = writer.open(index,mapping)
   if (readyToWrite == false) {
      
     writer.close()
      
     val msg = String.format("""Opening index '%s' and mapping '%s' for write failed.""",index,mapping)
     throw new Exception(msg)
      
   } else {
          
     /*
      * Set status to indicate that the data tracking has started
      */
     cache.addStatus(req,status.TRACKING_STARTED)
 
     req.task.split(":")(1) match {

       case "event" => {
         
         val source = prepareEvent(req.data)
         /*
          * Writing this source to the respective index throws an
          * exception in case of an error; note, that the writer is
          * automatically closed 
          */
         writer.write(index, mapping, source)        
        
       }       
       case "item" => {
      
         /*
          * Data preparation comprises the extraction of all common 
          * fields, i.e. timestamp, site, user and group. The 'item' 
          * field may specify a list of purchase items and has to be 
          * processed differently.
          */
         val source = prepareItem(req.data)
         /*
          * The 'item' field specifies a comma-separated list
          * of item (e.g.) product identifiers. Note, that every
          * item is actually indexed individually. This is due to
          * synergy effects with other data sources
          */
         val items = req.data(Names.ITEM_FIELD).split(",")
 
         /*
          * A trackable event may have a 'score' field assigned;
          * note, that this field is optional
          */
         val scores = if (req.data.contains(Names.REQ_SCORE)) req.data(Names.REQ_SCORE).split(",").map(_.toDouble) else Array.fill[Double](items.length)(0)

         val zipped = items.zip(scores)
         for  ((item,score) <- zipped) {
           /*
            * Set or overwrite the 'item' field in the respective source
            */
           source.put(Names.ITEM_FIELD, item)
           /*
            * Set or overwrite the 'score' field in the respective source
            */
           source.put(Names.SCORE_FIELD, score.asInstanceOf[Object])
           /*
            * Writing this source to the respective index throws an
            * exception in case of an error; note, that the writer is
            * automatically closed 
            */
           writer.write(index, mapping, source)
         }
         
       }      
       case _ => {
          
         val msg = messages.TASK_IS_UNKNOWN(uid,req.task)
         throw new Exception(msg)
          
       }
      
     }
 
     /*
      * Set status to indicate that the respective data have
      * been tracked sucessfully
      */
     cache.addStatus(req,status.TRACKING_FINISHED)
     
     val data = Map(Names.REQ_UID -> uid)
     new ServiceResponse(req.service,req.task,data,status.TRACKING_FINISHED)
  
   }
    
  }
   
  protected def prepareEvent(params:Map[String,String]):java.util.Map[String,Object]

  protected def prepareItem(params:Map[String,String]):java.util.Map[String,Object]
 
}
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
import de.kp.spark.core.elastic._

import org.elasticsearch.common.xcontent.XContentBuilder

class BaseTracker(config:Configuration) extends RootActor(config) {
  
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

       case "amount" => {
      
         val source = prepareAmount(req)
         /*
          * Writing this source to the respective index throws an
          * exception in case of an error; note, that the writer is
          * automatically closed 
          */
         writer.write(index, mapping, source)
         
       }
       case "event" => {
         
         val source = prepareEvent(req)
         /*
          * Writing this source to the respective index throws an
          * exception in case of an error; note, that the writer is
          * automatically closed 
          */
         writer.write(index, mapping, source)        
        
       }       
       case "feature" => {
      
         val source = prepareFeature(req)
         /*
          * Writing this source to the respective index throws an
          * exception in case of an error; note, that the writer is
          * automatically closed 
          */
         writer.write(index, mapping, source)
         
       }
       case "item" => {
      
         val sources = prepareItemJSON(req)
         /*
          * Writing these sources to the respective index throws an
          * exception in case of an error; note, that the writer is
          * automatically closed 
          */
         writer.writeBulkJSON(index, mapping, sources)
         
       }      
       case "product" => {
      
         val source = prepareProduct(req)
         /*
          * Writing this source to the respective index throws an
          * exception in case of an error; note, that the writer is
          * automatically closed 
          */
         writer.write(index, mapping, source)
         
       }
       case "sequence" => {
      
         val source = prepareSequence(req)
         /*
          * Writing this source to the respective index throws an
          * exception in case of an error; note, that the writer is
          * automatically closed 
          */
         writer.write(index, mapping, source)
         
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
    
  protected def prepareAmount(req:ServiceRequest):java.util.Map[String,Object] = {
    new ElasticAmountBuilder().createSource(req.data)
  }
  
  protected def prepareEvent(req:ServiceRequest):java.util.Map[String,Object] = {
    new ElasticEventBuilder().createSource(req.data)
  }
    
  protected def prepareFeature(req:ServiceRequest):java.util.Map[String,Object] = {
    new ElasticFeatureBuilder().createSource(req.data)
  }

  protected def prepareItemJSON(req:ServiceRequest):List[XContentBuilder] = {
   /*
    * Example request data:
    * 
    * "uid": "123456"
    * 
    * "index": "orders"
    * "type" : "products"
    * 
    * "site"    : "site-1"
    * "user"    : "user-1"
    * "timestamp: "1234567890"
    * "group"   : "group-1"
    * "item"    : "1,2,3,4,5,6,7"
    * 
    */   
    new ElasticItemBuilder().createSourceJSON(req.data)
  }
  
  protected def prepareProduct(req:ServiceRequest):java.util.Map[String,Object] = {
    new ElasticProductBuilder().createSource(req.data)    
  }
  
  protected def prepareSequence(req:ServiceRequest):java.util.Map[String,Object] = {
    new ElasticSequenceBuilder().createSource(req.data)    
  }
 
}
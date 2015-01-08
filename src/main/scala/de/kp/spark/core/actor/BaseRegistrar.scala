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

import de.kp.spark.core.{Configuration,Names}

import de.kp.spark.core.model._
import de.kp.spark.core.spec.FieldBuilder

class BaseRegistrar(config:Configuration) extends RootActor(config) {
  
  def receive = {
    
    case req:ServiceRequest => {
      
      val origin = sender    
      val uid = req.data(Names.REQ_UID)
      
      val response = try {
        register(req)
        
      } catch {
        case throwable:Throwable => failure(req,throwable.getMessage)
      }
      
      origin ! response
      context.stop(self)

    }
    
  }

  protected def register(req:ServiceRequest):ServiceResponse = {
    
    val uid = req.data(Names.REQ_UID)
    val topic = req.task.split(":")(1)
    
    topic match {
       
      case "amount" => {
        
        val fields = new FieldBuilder().build(req,topic)
        cache.addFields(req, fields.toList)
        
        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
      
      }
      case "event" => {
        
        val fields = new FieldBuilder().build(req,topic)
        cache.addFields(req, fields.toList)
        
        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
      
      }
      case "feature" => {
        /*
         * ********************************************
         *  Example:
         *  
         *  "names" -> "target,feature,feature,feature"
         *  "types" -> "string,double,double,string"
         *
         * ********************************************
         * 
         * It is important to have the names specified in the order
         * they are used (later) to retrieve the respective data
         */
        val names = req.data(Names.REQ_NAMES).split(",")
        val types = req.data(Names.REQ_TYPES).split(",")
        
        val fields = buildFields(names,types)
        cache.addFields(req, fields)    

        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
        
      }
      case "item" => {
        
        val fields = new FieldBuilder().build(req,topic)
        cache.addFields(req, fields.toList)
        
        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
                
      }        
      case "product" => {
        
        val fields = new FieldBuilder().build(req,topic)
        cache.addFields(req, fields)
        
        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
          
      }
      case "sequence" => {
        
        val fields = new FieldBuilder().build(req,topic)
        cache.addFields(req, fields)
        
        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
          
      }
      case "vector" => {
        
        val fields = new FieldBuilder().build(req,topic)
        cache.addFields(req, fields)
        
        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
          
      }
      case _ => {
          
         val msg = messages.TASK_IS_UNKNOWN(uid,req.task)
         throw new Exception(msg)
          
       }

    }
    
  }
  
  protected def buildFields(names:Array[String],types:Array[String]):List[Field] = List.empty[Field]
  
}
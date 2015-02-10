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
    
    val topics = List("event","item","point","sequence","state","vector")
    if (topics.contains(topic) == false) {
      
      val msg = messages.TASK_IS_UNKNOWN(uid,req.task)
      throw new Exception(msg)
      
    }
        
    val fields = new FieldBuilder().build(req,topic)
    cache.addFields(req, fields.toList)
        
    new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
    
  }
  
}
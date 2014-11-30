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

import akka.actor.{Actor,ActorLogging}

import de.kp.spark.core.Configuration

import de.kp.spark.core.model._
import de.kp.spark.core.redis.RedisCache

import de.kp.spark.core.model._

abstract class RootActor(config:Configuration) extends Actor with ActorLogging {
 
  protected val status = new BaseStatus()
  protected val serializer = new BaseSerializer()

  private val (host,port) = config.redis
  protected val cache = new RedisCache(host,port.toInt)
  
  protected def failure(req:ServiceRequest,message:String):ServiceResponse = {
    
    if (req == null) {
      val data = Map("message" -> message)
      new ServiceResponse("","",data,status.FAILURE)	
      
    } else {
      val data = Map("uid" -> req.data("uid"), "message" -> message)
      new ServiceResponse(req.service,req.task,data,status.FAILURE)	
    
    }
    
  }

  protected def serialize(resp:ServiceResponse) = serializer.serializeResponse(resp)
  
}
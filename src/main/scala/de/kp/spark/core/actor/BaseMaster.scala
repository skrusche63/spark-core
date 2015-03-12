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

import akka.actor.ActorRef

import akka.pattern.ask
import akka.util.Timeout

import akka.actor.{OneForOneStrategy, SupervisorStrategy}

import de.kp.spark.core.{Configuration,Names}
import de.kp.spark.core.model._

import scala.concurrent.duration.DurationInt
import scala.concurrent.Future

abstract class BaseMaster(config:Configuration) extends RootActor(config) {

  val (duration,retries,time) = config.actor   
	  	    
  implicit val ec = context.dispatcher
  implicit val timeout:Timeout = DurationInt(time).second

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries=retries,withinTimeRange = DurationInt(time).minutes) {
    case _ : Exception => SupervisorStrategy.Restart
  }
  
  def receive = {
    /*
     * This request is initiated by the Akka API; 
     * the respective responses MUST be serialized
     */
    case msg:String => {
      
	  val origin = sender

	  val req = serializer.deserializeRequest(msg)
	  val response = execute(req)
	  
      response.onSuccess {
        case result => origin ! serialize(result)
      }
      response.onFailure {
        case result => origin ! {
          serialize(failure(req,messages.GENERAL_ERROR(req.data(Names.REQ_UID))))	      
        }
	  }
      
    }
    /*
     * This request is initiated by the Rest API; 
     * the respective responses MUST not be serialized
     */
    case req:ServiceRequest => {

	  val origin = sender

	  val response = execute(req)	  
      response.onSuccess {
        case result => origin ! result
      }
      response.onFailure {
        case result => origin ! failure(req,messages.GENERAL_ERROR(req.data(Names.REQ_UID)))      
	  }
      
    }
  
    case _ => {

      val msg = messages.REQUEST_IS_UNKNOWN()          
      log.error(msg)

    }
    
  }

  protected def execute(req:ServiceRequest):Future[ServiceResponse] = {
	
    try {
     
      val Array(task,topic) = req.task.split(":")
      ask(actor(task),req).mapTo[ServiceResponse]
    
    } catch {
      
      case e:Exception => {
        Future {failure(req,e.getMessage)}         
      }
    
    }
     
  }
  
  protected def actor(worker:String):ActorRef

}
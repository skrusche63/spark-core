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
import de.kp.spark.core.Configuration

import de.kp.spark.core.Names
import de.kp.spark.core.model._

import scala.concurrent.duration.DurationInt

/**
 * The Supervisor polls the Redis based cache and informs the parent about the occurrence 
 * of a specific status value; to this end, an AliveMessage is sent every second to the 
 * actor itself that retrieves that latest status with respect to a certain 'site' and 'uid', 
 * compares the status with the provided one and informs the subscriber about a match
 */
class Supervisor(req:ServiceRequest,value:String,config:Configuration) extends RootActor(config) {

  implicit val ec = context.dispatcher

  val scheduledTask = context.system.scheduler.schedule(DurationInt(0).second, DurationInt(1).second,self,new AliveMessage())  
  
  override def postStop() {
    scheduledTask.cancel()
  }  

  def receive = {

    case message:AliveMessage => {
      
      val status = cache.status(req)
      /*
       * A 'status' is registered with respect to a certain 'site' and 'uid';
       * to determine whether a specific mining or model building task has
       * reached a status that is equal to the provided 'value', we have to
       * take care that also the 'service' and 'task' is equal
       */
      if ((req.service == status.service) && (req.task == status.task) && (value == status.value)) {
      
        val uid = req.data(Names.REQ_UID)
        context.parent ! StatusEvent(uid,req.service,req.task,value)
 
        context.stop(self)
        
      }      
      
    }
    
    case _ => {}
    
  }

}
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
  
  protected def track(req:ServiceRequest):ServiceResponse
  
}
package de.kp.spark.core.actor
/* Copyright (c) 2014 Dr. Krusche & Partner PartG
 * 
 * This file is part of the Spark-Core project
 * (https://github.com/skrusche63/spark-corestatus).
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

import scala.collection.mutable.ArrayBuffer

abstract class FeatureRegistrar(config:Configuration) extends RootActor(config) {
  
  def receive = {
    
    case req:ServiceRequest => {
      
      val origin = sender    
      val uid = req.data(Names.REQ_UID)
      
      val response = try {
        /*
         * ********************************************
         * 
         *  "uid" -> 123
         *  "names" -> "target,feature,feature,feature"
         *  "types" -> "string,double,double,string"
         *
         * ********************************************
         * 
         * It is important to have the names specified in the order
         * they are used (later) to retrieve the respective data
         */
        val names = req.data("names").split(",")
        val types = req.data("types").split(",")
        
        /* Unpack fields from request and register in Redis instance */
        val fields = buildFields(names,types)
 
        cache.addFields(req, fields)    
        new ServiceResponse(req.service,req.task,Map(Names.REQ_UID-> uid),status.SUCCESS)
        
      } catch {
        case throwable:Throwable => failure(req,throwable.getMessage)
      }
      
      origin ! response

    }
    
  }
  
  protected def buildFields(names:Array[String],types:Array[String]):Fields

}
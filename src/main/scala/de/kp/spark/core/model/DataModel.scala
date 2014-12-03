package de.kp.spark.core.model
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

/**
 * The Field and Fields classes are used to specify the fields with
 * respect to the data source provided, that have to be mapped onto
 * site,timestamp,user,group,item
 */
case class Field(
  name:String,datatype:String,value:String
)
case class Fields(items:List[Field])

/**
 * Listener specifies a remote akka service to send
 * notifications to
 */
case class Listener(
  timeout:Int, url:String
)
/**
 * ServiceRequest & ServiceResponse specify the content sent to 
 * and received from the different predictive engines
 */
case class ServiceRequest(
  service:String,task:String,data:Map[String,String]
)
case class ServiceResponse(
  service:String,task:String,data:Map[String,String],status:String
)

/**
 * Service requests are mapped onto status descriptions 
 * and are stored in a Redis instance
 */
case class Status(
  service:String,task:String,status:String,timestamp:Long
)

case class StatusList(items:List[Status])

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

import org.json4s._

import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read,write}

class BaseSerializer {
    
  implicit val formats = Serialization.formats(NoTypeHints)
  
  def serializeFields(fields:Fields):String = write(fields) 
  def deserializeFields(fields:String):Fields = read[Fields](fields)
  
  def serializeParams(params:Params):String = write(params) 
  def deserializeParams(params:String):Params = read[Params](params)

  def deserializeResponse(response:String):ServiceResponse = read[ServiceResponse](response)
  def serializeResponse(response:ServiceResponse):String = write(response)
  
  def deserializeRequest(request:String):ServiceRequest = read[ServiceRequest](request)
  def serializeRequest(request:ServiceRequest):String = write(request)

  def serializeStatus(status:Status):String = write(status)
  def deserializeStatus(status:String):Status = read[Status](status)

  def serializeStatusList(statuses:StatusList):String = write(statuses)
  def deserializeStatusList(statuses:String):StatusList = read[StatusList](statuses)

}
package de.kp.spark.core.redis
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

import java.util.Date
import scala.collection.JavaConversions._

import de.kp.spark.core.model._
import de.kp.spark.core.model.BaseSerializer

class RedisCache {

  val client  = RedisClient()
  val serializer = new BaseSerializer()

  def addFields(req:ServiceRequest,fields:Fields) {
    
    val now = new Date()
    val timestamp = now.getTime()
    
    val k = "fields:" + req.service + ":" + req.data("uid")
    val v = "" + timestamp + ":" + serializer.serializeFields(fields)
    
    client.zadd(k,timestamp,v)
    
  }
  
  def addRequest(req:ServiceRequest) {
    
    val now = new Date()
    val timestamp = now.getTime()
    
    val k = "request:" + req.service
    val v = "" + timestamp + ":" + serializer.serializeRequest(req)
    
    client.lpush(k,v)
    
  }
  
  def addStatus(req:ServiceRequest, status:String) {
   
    val (uid,service,task) = (req.data("uid"),req.service,req.task)
    
    val now = new Date()
    val timestamp = now.getTime()
    
    val k = "status:" + service + ":" + uid
    val v = "" + timestamp + ":" + serializer.serializeStatus(Status(service,task,status))
    
    client.zadd(k,timestamp,v)
    
  }
  
  def fieldsExist(req:ServiceRequest):Boolean = {

    val k = "fields:" + req.service + ":" + req.data("uid")
    client.exists(k)
    
  }
  
  def statusExists(req:ServiceRequest):Boolean = {

    val k = "status:" + req.service + ":" + req.data("uid")
    client.exists(k)
    
  }
  
  def fields(req:ServiceRequest):Fields = {

    val k = "fields:" + req.service + ":" + req.data("uid")
    val metas = client.zrange(k, 0, -1)

    if (metas.size() == 0) {
      new Fields(List.empty[Field])
    
    } else {
      
      val latest = metas.toList.last
      val Array(timestamp,fields) = latest.split(":")
      
      serializer.deserializeFields(fields)
     
    }

  }
  
  def requestsTotal(service:String):Long = {

    val k = "request:" + service
    if (client.exists(k)) client.llen(k) else 0
    
  }
 
  def requests(service:String,start:Long,end:Long):List[(Long,ServiceRequest)] = {
    
    val k = "request:" + service
    val requests = client.lrange(k, start, end)
    
    requests.map(request => {
      
      val Array(ts,req) = request.split(":")
      (ts.toLong,serializer.deserializeRequest(req))
      
    }).toList
    
  }
  
  def status(req:ServiceRequest):String = {

    val k = "job:" + req.service + ":" + req.data("uid")
    val data = client.zrange(k, 0, -1)

    if (data.size() == 0) {
      null
    
    } else {
      
      /* Format: timestamp:status */
      val last = data.toList.last
      val Array(timestamp,status) = last.split(":")
      
      val job = serializer.deserializeStatus(status)
      job.status
      
    }

  }
  
  def statuses(req:ServiceRequest):List[(Long,Status)] = {
    
    val k = "job:" + req.service + ":" + req.data("uid")
    val data = client.zrange(k, 0, -1)

    if (data.size() == 0) {
      null
    
    } else {
      
      data.map(record => {
        
        val Array(timestamp,status) = record.split(":")
        (timestamp.toLong,serializer.deserializeStatus(status))
        
      }).toList
      
    }
    
  }
}
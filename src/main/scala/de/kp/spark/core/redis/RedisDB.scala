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
import de.kp.spark.core.Names
import de.kp.spark.core.model._

import scala.collection.JavaConversions._

class RedisDB(host:String,port:Int) extends Serializable {

  val client = RedisClient(host,port)
  /**
   * Register the path to similarity matrix; the similarity matrix
   * is built by the Context-Aware Analysis engine and specifies the
   * similarity of features on the basis of thier interaction behavior.
   * 
   * A factorization model computes the interaction or correlation of
   * specific predictor variables. A predictive variable can specify
   * a certain user or item and also contextual variables.
   * 
   * Note, that the name of the model is part of the key variable and
   * must be unique. 
   */
  def addMatrix(req:ServiceRequest,matrix:String) {
   
    val now = new java.util.Date()
    val timestamp = now.getTime()
    
    val k = "matrix:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME)
    val v = "" + timestamp + ":" + matrix
    
    client.zadd(k,timestamp,v)
    
  }
  
  def matrixExists(req:ServiceRequest):Boolean = {

    val k = "matrix:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME)
    client.exists(k)
    
  }
  
  def matrix(req:ServiceRequest):String = {

    val k = "matrix:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME)
    val matrices = client.zrange(k, 0, -1)

    if (matrices.size() == 0) {
      null
    
    } else {
      
      val last = matrices.toList.last
      val Array(timestamp,path) = last.split(":")
      
      path
      
    }
  
  }
  /**
   * A trained factorization (or polynom) model is persisted on the 
   * HDFS file system; the REDIS instance holds the path to the model.
   * 
   * Besides registering factorization (polynom) models, this method
   * is also used to register the path to a matrix (ALS) factorization
   * model.
   * 
   * Note, that the name of the model is part of the key variable and
   * must be unique. 
   */
  def addModel(req:ServiceRequest, model:String) {
   
    val now = new java.util.Date()
    val timestamp = now.getTime()
    
    val k = "model:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME)
    val v = "" + timestamp + ":" + model
    
    client.zadd(k,timestamp,v)
    
  }
  
  def modelExists(req:ServiceRequest):Boolean = {

    val k = "model:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME)
    client.exists(k)
    
  }
  
  def model(req:ServiceRequest):String = {

    val k = "model:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME)
    val models = client.zrange(k, 0, -1)

    if (models.size() == 0) {
      null
    
    } else {
      
      val last = models.toList.last
      last.split(":")(1)
      
    }
  
  }
  /**
   * This method registers the unique identifier of a certain user in the Redis
   * instance; this information is used by the Preference engine, Context-Aware
   * Analysis engine and others
   */
  def addUser(req:ServiceRequest,uid:String) {
    
    val k = "user:" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME)
    val v = uid
    
    client.rpush(k,v)
    
  }

  def users (req:ServiceRequest):Seq[String] = {
       
    val k = "user:" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME)
    val data = client.lrange(k, 0, -1)

    val users = if (data.size() == 0) List.empty[String] else data.toList
    users
    
  }
  
  /**
   * This method registers the unique identifier of a certain item in the Redis
   * instance; this information is used by the Preference engine, Context-Aware
   * Analysis engine and others
   */
  def addItem(req:ServiceRequest,iid:String) {
    
    val k = "item:" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME)
    val v = iid
    
    client.rpush(k,v)
    
  }

  def items (req:ServiceRequest):Seq[String] = {
       
    val k = "item:" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME)
    val data = client.lrange(k, 0, -1)

    val items = if (data.size() == 0) List.empty[String] else data.toList
    items
    
  }
  
}
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

  private val client = RedisClient(host,port)
  private val serializer = new BaseSerializer()
  
  def exists(k:String):Boolean = client.exists(k)
  
  def getClient = client
  
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
    
    val k = "matrix:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME) + ":"  + req.data(Names.REQ_MATRIX)
    val v = "" + timestamp + ":" + matrix
    
    client.zadd(k,timestamp,v)
    
  }
  
  def matrixExists(req:ServiceRequest):Boolean = {

    val k = "matrix:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME) + ":"  + req.data(Names.REQ_MATRIX)
    client.exists(k)
    
  }
  
  def matrix(req:ServiceRequest):String = {

    val k = "matrix:" + req.data(Names.REQ_UID) + ":"  + req.data(Names.REQ_NAME) + ":"  + req.data(Names.REQ_MATRIX)
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
  
  def addEvent(req:ServiceRequest,eid:Int,ename:String) {
    
    val k = "event:" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME)
    val v = "" + eid + ":" +ename
    
    client.rpush(k,v)
    
  }
  
  def events (req:ServiceRequest):Seq[String] = {
       
    val k = "event:" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME)
    val data = client.lrange(k, 0, -1)

    val events = if (data.size() == 0) List.empty[String] else data.map(x => x.split(":")(1))
    events
    
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
  
  def addRating(req:ServiceRequest,rating:String) {
    
    val k = "rating:" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME)
    val v = rating
    
    client.rpush(k,v)

  }

  def addRules(req:ServiceRequest, rules:Rules) {
   
    val now = new java.util.Date()
    val timestamp = now.getTime()
    
    val k = ruleKey(req)
    val v = "" + timestamp + ":" + serializer.serializeRules(rules)
    
    client.zadd(k,timestamp,v)
    
  }
   
  def rulesExist(req:ServiceRequest):Boolean = {

    val k = ruleKey(req)
    client.exists(k)
    
  }
   
  def rulesAsList(req:ServiceRequest):List[Rule] = {

    val k = ruleKey(req)
    val rules = client.zrange(k, 0, -1)

    if (rules.size() == 0) {
      List.empty[Rule]
    
    } else {
      
      val last = rules.toList.last
      serializer.deserializeRules(last.split(":")(1)).items
      
    }
  
  }
 
  def rulesAsString(req:ServiceRequest):String = {

    val k = ruleKey(req)
    val rules = client.zrange(k, 0, -1)

    if (rules.size() == 0) {
      serializer.serializeRules(new Rules(List.empty[Rule]))
    
    } else {
      
      val last = rules.toList.last
      last.split(":")(1)
      
    }
  
  }
  /**
   * Retrieve those rules, where the antecedents match the provided ones;
   * we distinguish two different matching methods, lazy and strict.
   */
  def rulesByAntecedent(req:ServiceRequest,antecedent:List[Int]):String = {
  
    val service = req.service
    val items = service match {
      
      case "association" => rulesAsList(req).filter(rule => isLazyEqual(rule.antecedent,antecedent))
      case "series"      => rulesAsList(req).filter(rule => isStrictEqual(rule.antecedent,antecedent))
      
      case _ => throw new Exception("Service not supported.")
     
    }
    serializer.serializeRules(new Rules(items))
    
  } 
  /**
   * Retrieve those rules, where the consequents match the provided ones;
   * we distinguish two different matching methods, lazy and strict.
   */
  def rulesByConsequent(req:ServiceRequest,consequent:List[Int]):String = {
  
    val service = req.service
    val items = service match {
      
      case "association" => rulesAsList(req).filter(rule => isLazyEqual(rule.consequent,consequent))
      case "series"      => rulesAsList(req).filter(rule => isStrictEqual(rule.consequent,consequent))
      
      case _ => throw new Exception("Service not supported.")
     
    }
    serializer.serializeRules(new Rules(items))

  } 
  
  private def patternKey(req:ServiceRequest):String = {
    "pattern:" + req.data(Names.REQ_SITE) + ":" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME) 
  }

  private def ruleKey(req:ServiceRequest):String = {
    "rule:" + req.data(Names.REQ_SITE) + ":" + req.data(Names.REQ_UID) + ":" + req.data(Names.REQ_NAME) 
  }
  
  private def isLazyEqual(itemset1:List[Int],itemset2:List[Int]):Boolean = {
    
    val intersect = itemset1.intersect(itemset2)
    intersect.size == itemset1.size
    
  }
   
  private def isStrictEqual(itemset1:List[Int],itemset2:List[Int]):Boolean = {
    
    if (itemset1.length != itemset2.length) {
      return false
    }
    
    val max = itemset1.zip(itemset2).map(x => Math.abs(x._1 -x._2)).max
    (max == 0)
    
  }
  
  def addPatterns(req:ServiceRequest, patterns:Patterns) {
   
    val now = new java.util.Date()
    val timestamp = now.getTime()
    
    val k = patternKey(req)
    val v = "" + timestamp + ":" + serializer.serializePatterns(patterns)
    
    getClient.zadd(k,timestamp,v)
    
  }
 
  def patternsExist(req:ServiceRequest):Boolean = {

    val k = patternKey(req)
    exists(k)
    
  }
  
  def patterns(req:ServiceRequest):String = {

    val k = patternKey(req)
    val patterns = getClient.zrange(k, 0, -1)

    if (patterns.size() == 0) {
      serializer.serializePatterns(new Patterns(List.empty[Pattern]))
    
    } else {
      
      val last = patterns.toList.last
      last.split(":")(1)
      
    }
  
  }

}
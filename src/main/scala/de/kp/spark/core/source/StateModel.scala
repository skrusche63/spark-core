package de.kp.spark.core.source
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

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import de.kp.spark.core.Names
import de.kp.spark.core.model._

import de.kp.spark.core.spec.Fields

class StateModel(@transient sc:SparkContext) extends Serializable {

  def buildCassandra(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,Long,String)] = {
  
    val spec = sc.broadcast(fields.mapping)
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)).asInstanceOf[String]
      val user = data(spec.value(Names.USER_FIELD)).asInstanceOf[String] 

      val timestamp = data(spec.value(Names.TIMESTAMP_FIELD)).asInstanceOf[Long]
      val state  = data(spec.value(Names.STATE_FIELD)).asInstanceOf[String]
      
      (site,user,timestamp,state)
      
    })
    
  }
  
  def buildElastic(req:ServiceRequest,rawset:RDD[Map[String,String]],fields:Fields):RDD[(String,String,Long,String)] = {
    
    val spec = sc.broadcast(fields.mapping)
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD))
      val user = data(spec.value(Names.USER_FIELD))      

      val timestamp = data(spec.value(Names.TIMESTAMP_FIELD)).toLong
      val state  = data(spec.value(Names.STATE_FIELD))
      
      (site,user,timestamp,state)
      
    })
    
  }

  def buildFile(req:ServiceRequest, rawset:RDD[String]):RDD[(String,String,Long,String)] = {
    
    rawset.map {line =>
      
      val Array(site,user,timestamp,state) = line.split(',')
      (site,user,timestamp.toLong,state)

    }
    
  }
  
  def buildHBase(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,Long,String)] = {
  
    val spec = sc.broadcast(fields.mapping)
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)).asInstanceOf[String]
      val user = data(spec.value(Names.USER_FIELD)).asInstanceOf[String] 

      val timestamp = data(spec.value(Names.TIMESTAMP_FIELD)).asInstanceOf[Long]
      val state  = data(spec.value(Names.STATE_FIELD)).asInstanceOf[String]
      
      (site,user,timestamp,state)
      
    })
    
  }
  
  def buildJDBC(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,Long,String)] = {
  
    val spec = sc.broadcast(fields.mapping)
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)).asInstanceOf[String]
      val user = data(spec.value(Names.USER_FIELD)).asInstanceOf[String] 

      val timestamp = data(spec.value(Names.TIMESTAMP_FIELD)).asInstanceOf[Long]
      val state  = data(spec.value(Names.STATE_FIELD)).asInstanceOf[String]
      
      (site,user,timestamp,state)
      
    })
    
  }
  
  def buildMongo(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,Long,String)] = {
  
    val spec = sc.broadcast(fields.mapping)
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)).asInstanceOf[String]
      val user = data(spec.value(Names.USER_FIELD)).asInstanceOf[String] 

      val timestamp = data(spec.value(Names.TIMESTAMP_FIELD)).asInstanceOf[Long]
      val state  = data(spec.value(Names.STATE_FIELD)).asInstanceOf[String]
      
      (site,user,timestamp,state)
      
    })
    
  }
 
  def buildParquet(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,Long,String)] = {
    
    val spec = sc.broadcast(fields.mapping)
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)).asInstanceOf[String]
      val user = data(spec.value(Names.USER_FIELD)).asInstanceOf[String] 

      val timestamp = data(spec.value(Names.TIMESTAMP_FIELD)).asInstanceOf[Long]
      val state  = data(spec.value(Names.STATE_FIELD)).asInstanceOf[String]
      
      (site,user,timestamp,state)
      
    })
    
  }

}
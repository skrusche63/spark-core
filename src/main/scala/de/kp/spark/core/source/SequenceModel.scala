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

import de.kp.spark.core.model._
import de.kp.spark.core.spec.Fields

import scala.collection.mutable.ArrayBuffer

class SequenceModel(@transient sc:SparkContext) extends Serializable {
  
  def buildElastic(req:ServiceRequest,rawset:RDD[Map[String,String]],fields:Fields):RDD[(String,String,String,Long,String)] = {
 
    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val site = data(spec.value("site")._1)
      val timestamp = data(spec.value("timestamp")._1).toLong

      val user = data(spec.value("user")._1)      
      val group = data(spec.value("group")._1)

      val item  = data(spec.value("item")._1)
      
      (site,user,group,timestamp,item)
      
    })

  }
  
  def buildFile(req:ServiceRequest,rawset:RDD[String]):RDD[(String,String,String,Long,String)] = {
    
    rawset.map(line => {
      
      val Array(site,user,group,timestamp,item) = line.split(",")  
      (site,user,group,timestamp.toLong,item)
    
    })
    
  }
  
  def buildJDBC(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,String,Long,String)] = {

    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val site = data(spec.value("site")._1).asInstanceOf[String]
      val timestamp = data(spec.value("timestamp")._1).asInstanceOf[Long]

      val user = data(spec.value("user")._1).asInstanceOf[String] 
      val group = data(spec.value("group")._1).asInstanceOf[String]
      
      val item  = data(spec.value("item")._1).asInstanceOf[String]
      
      (site,user,group,timestamp,item)
      
    })

  }
   
  def buildParquet(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,String,Long,String)] = {

    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val site = data(spec.value("site")._1).asInstanceOf[String]
      val timestamp = data(spec.value("timestamp")._1).asInstanceOf[Long]

      val user = data(spec.value("user")._1).asInstanceOf[String] 
      val group = data(spec.value("group")._1).asInstanceOf[String]
      
      val item  = data(spec.value("item")._1).asInstanceOf[String]
      
      (site,user,group,timestamp,item)
      
    })

  }
 
  def buildPiwik(req:ServiceRequest,rawset:RDD[Map[String,Any]]):RDD[(String,String,String,Long,String)] = {
    
    rawset.map(row => {
      
      val site = row("idsite").asInstanceOf[Long]
      val timestamp  = row("server_time").asInstanceOf[Long]

      /* Convert 'idvisitor' into a HEX String representation */
      val idvisitor = row("idvisitor").asInstanceOf[Array[Byte]]     
      val user = new java.math.BigInteger(1, idvisitor).toString(16)

      val group = row("idorder").asInstanceOf[String]
      val item  = row("idaction_sku").asInstanceOf[Long]
      
      (site.toString,user,group,timestamp,item.toString)
      
    })
    
  }

}
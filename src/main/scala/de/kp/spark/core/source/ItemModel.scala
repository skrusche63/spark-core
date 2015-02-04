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

class ItemModel(@transient sc:SparkContext) extends Serializable {
  
  def buildElastic(req:ServiceRequest,rawset:RDD[Map[String,String]],fields:Fields):RDD[(String,String,String,Int)] = {

    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)._1)
      val user = data(spec.value(Names.USER_FIELD)._1)      

      val group = data(spec.value(Names.GROUP_FIELD)._1)
      val item  = data(spec.value(Names.ITEM_FIELD)._1).toInt
      
      (site,user,group,item)
      
    })

  }
  
  def buildFile(req:ServiceRequest,rawset:RDD[String]):RDD[(String,String,String,Int)] = {
    
    rawset.map(line => {
      
      val Array(site,user,group,item) = line.split(",")
      (site,user,group,item.toInt)
    
    })
    
  }
  
  def buildJDBC(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,String,Int)] = {
        
    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)._1).asInstanceOf[String]
      val user = data(spec.value(Names.USER_FIELD)._1).asInstanceOf[String] 

      val group = data(spec.value(Names.GROUP_FIELD)._1).asInstanceOf[String]
      val item  = data(spec.value(Names.ITEM_FIELD)._1).asInstanceOf[Int]
      
      (site,user,group,item)
      
    })

  }
  
  def buildParquet(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(String,String,String,Int)] = {
        
    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val site = data(spec.value(Names.SITE_FIELD)._1).asInstanceOf[String]
      val user = data(spec.value(Names.USER_FIELD)._1).asInstanceOf[String] 

      val group = data(spec.value(Names.GROUP_FIELD)._1).asInstanceOf[String]
      val item  = data(spec.value(Names.ITEM_FIELD)._1).asInstanceOf[Int]
      
      (site,user,group,item)
      
    })

  }
    
  def buildPiwik(req:ServiceRequest,rawset:RDD[Map[String,Any]]):RDD[(String,String,String,Int)] = {
    
    rawset.map(row => {
      
      val site = row("idsite").asInstanceOf[Long]
      /* Convert 'idvisitor' into a HEX String representation */
      val idvisitor = row("idvisitor").asInstanceOf[Array[Byte]]     
      val user = new java.math.BigInteger(1, idvisitor).toString(16)

      val group = row("idorder").asInstanceOf[String]
      val item  = row("idaction_sku").asInstanceOf[Long]
      /* 
       * Convert 'site' to String representation to be 
       * harmonized with other data sources
       */
      (site.toString,user,group,item.toInt)
      
    })
    
  }
  
}
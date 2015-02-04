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

class VectorModel(@transient sc:SparkContext) extends Serializable {
  
  def buildElastic(req:ServiceRequest,rawset:RDD[Map[String,String]], fields:Fields):RDD[(Long,Long,String,Double)] = {
   
    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val row = data(spec.value(Names.ROW_FIELD)).toLong
      val col = data(spec.value(Names.COL_FIELD)).toLong

      val label = data(spec.value(Names.LBL_FIELD))
      val value = data(spec.value(Names.VAL_FIELD)).toDouble
      
      (row,col,label,value)
      
    })
    
  }
  
  def buildFile(req:ServiceRequest,rawset:RDD[String]):RDD[(Long,Long,String,Double)] = {
    
    rawset.map(line => {
      
      val Array(row,col,label,value) = line.split(",")  
      (row.toLong,col.toLong,label,value.toDouble)
    
    })
    
  }
  
  def buildParquet(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(Long,Long,String,Double)] = {
    
    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val row = data(spec.value(Names.ROW_FIELD)).asInstanceOf[Long]
      val col = data(spec.value(Names.COL_FIELD)).asInstanceOf[Long]

      val label = data(spec.value(Names.LBL_FIELD)).asInstanceOf[String] 
      val value = data(spec.value(Names.VAL_FIELD)).asInstanceOf[Double] 
      
      (row,col,label,value)
      
    })
    
  }
  
  def buildJDBC(req:ServiceRequest,rawset:RDD[Map[String,Any]],fields:Fields):RDD[(Long,Long,String,Double)] = {
    
    val spec = sc.broadcast(fields.get(req))
    rawset.map(data => {
      
      val row = data(spec.value(Names.ROW_FIELD)).asInstanceOf[Long]
      val col = data(spec.value(Names.COL_FIELD)).asInstanceOf[Long]

      val label = data(spec.value(Names.LBL_FIELD)).asInstanceOf[String] 
      val value = data(spec.value(Names.VAL_FIELD)).asInstanceOf[Double] 
      
      (row,col,label,value)
       
    })
    
  }

}
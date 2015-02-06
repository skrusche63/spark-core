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

import de.kp.spark.core.{Configuration,Names}
import de.kp.spark.core.io.CassandraReader

class CassandraSource(@transient sc:SparkContext) extends Serializable {
 
  def connect(config:Configuration,req:ServiceRequest,columns:List[String]=List.empty[String]):RDD[Map[String,Any]] = {
    
    val keyspace = req.data("keyspace")
    val table = req.data("table")
    
    new CassandraReader(sc).read(config,keyspace,table,columns)

  }

}
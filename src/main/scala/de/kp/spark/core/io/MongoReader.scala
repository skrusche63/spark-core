package de.kp.spark.core.io
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

import com.mongodb.hadoop.MongoInputFormat
import org.bson.BSONObject

import de.kp.spark.core.{Configuration => Config,Names}
import org.apache.hadoop.conf.{Configuration => HadoopConfig}

import scala.collection.mutable.HashMap
import scala.collection.JavaConversions._

class MongoReader {
  
  def readRDD(@transient sc:SparkContext,config:Config):RDD[Map[String,Any]] = {
    
    val conf = config.mongo
    
    val source = sc.newAPIHadoopRDD(conf, classOf[MongoInputFormat], classOf[Object], classOf[BSONObject])
    source.map(x => toMap(x._2))
    
  }

  private def toMap(obj:BSONObject):Map[String,Any] = {
    
    val data = HashMap.empty[String,Any]
    
    val keys = obj.keySet()
    for (k <- keys) {
      
      val v = obj.get(k)
      data += k -> v
    
    }
    
    data.toMap
    
  }
}
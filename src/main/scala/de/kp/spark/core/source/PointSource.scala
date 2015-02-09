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

import de.kp.spark.core.{Names,Configuration}

import de.kp.spark.core.model._
import de.kp.spark.core.spec.Fields

class PointSource (@transient sc:SparkContext,config:Configuration,fields:Fields) {

  private val model = new PointModel(sc)
  
  def connect(req:ServiceRequest):RDD[(Long,Long,String,Double)] = {
 
    val source = req.data(Names.REQ_SOURCE)
    source match {
      
      case Sources.CASSANDRA => {
       
        val rawset = new CassandraSource(sc).connect(config,req,fields.names)
        model.buildCassandra(req,rawset,fields)

      }
      case Sources.ELASTIC => {
       
       val rawset = new ElasticSource(sc).connect(config,req)
       model.buildElastic(req,rawset,fields)
       
      } 
      case Sources.FILE => {
       
        val store = req.data(Names.REQ_URL)
       
        val rawset = new FileSource(sc).connect(store,req)
        model.buildFile(req,rawset)
        
      }
      case Sources.HBASE => {
        
        val rawset = new HBaseSource(sc).connect(config,req,fields.names,fields.types)
        model.buildHBase(req,rawset,fields)

      }
      case Sources.JDBC => {
       
        val rawset = new JdbcSource(sc).connect(config,req,fields.names)
        model.buildJDBC(req,rawset,fields)

      }
      case Sources.MONGODB => {
       
        val rawset = new MongoSource(sc).connect(config,req)
        model.buildMongo(req,rawset,fields)

      }
      case Sources.PARQUET => {
       
        val store = req.data(Names.REQ_URL)
        
        val rawset = new ParquetSource(sc).connect(store,req)
        model.buildParquet(req,rawset,fields)

      }
      
      case _ => null
      
    }

  }
  
}
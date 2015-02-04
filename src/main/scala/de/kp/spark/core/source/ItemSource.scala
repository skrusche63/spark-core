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

import de.kp.spark.core.{Configuration,Names}
import de.kp.spark.core.model._

import de.kp.spark.core.spec.Fields

/**
 * An ItemSource is an abstraction layer on top of different physical 
 * data source to retrieve a transaction database compatible with the 
 * Top-K and Top-KNR algorithm of SPMF
 */
class ItemSource(@transient sc:SparkContext,config:Configuration,fields:Fields) {
  
  private val model = new ItemModel(sc)  
  def itemDS(req:ServiceRequest):RDD[(Int,Array[Int])] = {
    
    val source = req.data(Names.REQ_SOURCE)
    source match {
      /* 
       * Discover top k association rules from transaction database persisted 
       * as an appropriate search index from Elasticsearch; the configuration
       * parameters are retrieved from the service configuration 
       */    
      case Sources.ELASTIC => {
        
        val rawset = new ElasticSource(sc).connect(config,req)
        model.buildElastic(req,rawset,fields)
        
      }
      /* 
       * Discover top k association rules from transaction database persisted 
       * as a file on the (HDFS) file system; the configuration parameters are 
       * retrieved from the service configuration  
       */    
      case Sources.FILE => {
       
        val store = req.data(Names.REQ_URL)
         
        val rawset = new FileSource(sc).connect(store,req)
        model.buildFile(req,rawset)
        
      }
      /*
       * Retrieve Top-K association rules from transaction database persisted 
       * as an appropriate table from a JDBC database; the configuration parameters 
       * are retrieved from the service configuration
       */
      case Sources.JDBC => {
    
        val names = fields.get(req).map(kv => kv._2._1).toList    
        
        val rawset = new JdbcSource(sc).connect(config,req,names)
        model.buildJDBC(req,rawset,fields)
        
      }
      /*
       * Retrieve Top-K association rules from transaction database persisted 
       * as parquet file; the configuration parameters are retrieved from the 
       * service configuration
       */
      case Sources.PARQUET => {
       
        val store = req.data(Names.REQ_URL)
       
        val rawset = new ParquetSource(sc).connect(store,req)
        model.buildParquet(req,rawset,fields)
        
      }
      /*
       * Retrieve Top-K association rules from transaction database persisted 
       * as an appropriate table from a Piwik database; the configuration parameters 
       * are retrieved from the service configuration
       */
      case Sources.PIWIK => {
        
        val rawset = new PiwikSource(sc).connect(config,req)
        model.buildPiwik(req,rawset)
        
      }
            
      case _ => null
      
   }

  }

}
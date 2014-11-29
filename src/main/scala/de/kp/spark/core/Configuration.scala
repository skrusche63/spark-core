package de.kp.spark.core
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

import com.typesafe.config.ConfigFactory
import org.apache.hadoop.conf.{Configuration => HConf}

object Configuration {

  val ES_QUERY:String = "es.query"
  val ES_RESOURCE:String = "es.resource"
  
    /* Load configuration for router */
  val path = "application.conf"
  val config = ConfigFactory.load(path)

  def actor():(Int,Int,Int) = {
  
    val cfg = config.getConfig("actor")

    val duration = cfg.getInt("duration")
    val retries = cfg.getInt("retries")  
    val timeout = cfg.getInt("timeout")
    
    (duration,retries,timeout)
    
  }
  /**
   * This method retrieves a Hadoop configuration
   * th access Elasticsearch
   */
  def elastic():HConf = {
  
    val cfg = config.getConfig("elastic")
    val conf = new HConf()                          

    conf.set("es.nodes",cfg.getString("es.nodes"))
    conf.set("es.port",cfg.getString("es.port"))

    conf
    
  }
   
  def file():String = {
  
    val cfg = config.getConfig("file")
    cfg.getString("path")   
    
  }
 
  def mysql():(String,String,String,String) = {

   val cfg = config.getConfig("mysql")
  
   val url = cfg.getString("url")
   val db  = cfg.getString("database")
  
   val user = cfg.getString("user")
   val password = cfg.getString("password")
    
   (url,db,user,password)
   
  }
  /**
   * This method retrieves Typesafe Spray configuration
   * data for the REST API from the configuration file
   */
  def rest():(String,Int) = {
      
    val cfg = config.getConfig("rest")
      
    val host = cfg.getString("host")
    val port = cfg.getInt("port")

    (host,port)
    
  }
  /**
   * This method retrieves Apache Spark configuration
   * data from the configuration file
   */
  def spark():Map[String,String] = {
  
    val cfg = config.getConfig("spark")
    
    Map(
      "spark.executor.memory"          -> cfg.getString("spark.executor.memory"),
	  "spark.kryoserializer.buffer.mb" -> cfg.getString("spark.kryoserializer.buffer.mb")
    )

  }
  
}
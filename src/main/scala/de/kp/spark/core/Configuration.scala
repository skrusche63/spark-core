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
import org.apache.hadoop.conf.{Configuration => HConf}

trait Configuration {

  /**
   * This method retrieves the basic actor configuration
   * parameters, comprising duration, retries and timeout
   */
  def actor:(Int,Int,Int)
  
  /**
   * This method retrieves a Hadoop configuration
   * to access Elasticsearch
   */
  def elastic:HConf
  /** 
   * This method retrieves the path from the configuration
   * where to access a file source or a parquet file from 
   * the HDFS 
   */ 
  def input:List[String]

  /**
   * This method retrieves a Hadoop configuration
   * to access MongoDB
   */
  def mongo:HConf
   /**
    * This method retrieves the access parameter for a MySQL
    * data source, comprising url, db, user, password
    */
  def mysql:(String,String,String,String)

  /** 
   * This method retrieves the path from the configuration
   * where to save a text file or parquet file to the HDFS 
   */ 
  def output:List[String]

  /**
   * This method retrieves the access parameter for the
   * internally used Redis Instance: (host,port)
   */
  def redis:(String,String)
  /**
   * This method retrieves (host, port) for the REST API
   */
  def rest:(String,Int)
  /**
   * This method retrieves Apache Spark configuration
   */
  def spark:Map[String,String]
  
}
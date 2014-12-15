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
import org.apache.spark.SparkContext._

import org.apache.spark.sql.SQLContext
import org.apache.spark.rdd.RDD

import de.kp.spark.core.model._

class ParquetWriter(@transient sc:SparkContext) {
  /**
   * This method writes 'item' objects to a parquet file
   */
  def writeItems(store:String,dataset:RDD[ItemObject]) {

    val sqlCtx = new SQLContext(sc)
    import sqlCtx.createSchemaRDD

    /* 
     * The RDD is implicitly converted to a SchemaRDD by createSchemaRDD, 
     * allowing it to be stored using Parquet. 
     */
    dataset.saveAsParquetFile(store)

  }
  /**
   * This method writes 'rule' objects to a parquet file
   */
  def writeRules(store:String,dataset:RDD[RuleObject]) {

    val sqlCtx = new SQLContext(sc)
    import sqlCtx.createSchemaRDD

    /* 
     * The RDD is implicitly converted to a SchemaRDD by createSchemaRDD, 
     * allowing it to be stored using Parquet. 
     */
    dataset.saveAsParquetFile(store)

  }
  /**
   * This method writes scored 'event' objects to a parquet file
   */
  def writeScoredEvents(store:String,dataset:RDD[EventScoreObject]) {

    val sqlCtx = new SQLContext(sc)
    import sqlCtx.createSchemaRDD

    /* 
     * The RDD is implicitly converted to a SchemaRDD by createSchemaRDD, 
     * allowing it to be stored using Parquet. 
     */
    dataset.saveAsParquetFile(store)

  }  
  /**
   * This method writes scored 'item' objects to a parquet file
   */
  def writeScoredItems(store:String,dataset:RDD[ItemScoreObject]) {

    val sqlCtx = new SQLContext(sc)
    import sqlCtx.createSchemaRDD

    /* 
     * The RDD is implicitly converted to a SchemaRDD by createSchemaRDD, 
     * allowing it to be stored using Parquet. 
     */
    dataset.saveAsParquetFile(store)

  }
  /**
   * This method writes scored 'targeted point' objects to a parquet file
   */
  def writeTargetedPoints(store:String,dataset:RDD[TargetedPointObject]) {

    val sqlCtx = new SQLContext(sc)
    import sqlCtx.createSchemaRDD

    /* 
     * The RDD is implicitly converted to a SchemaRDD by createSchemaRDD, 
     * allowing it to be stored using Parquet. 
     */
    dataset.saveAsParquetFile(store)

  }
  
}
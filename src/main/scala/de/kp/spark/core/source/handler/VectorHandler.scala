package de.kp.spark.core.source.handler
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

object VectorHandler {
  
  /**
   * This method creates a set of labeled datapoints that are 
   * used for clustering or similarity analysis
   */
  def vector2LabeledPoints(dataset:RDD[(Long,Long,String,Double)]):RDD[LabeledPoint] = {
  
    val sc = dataset.sparkContext
    /*
     * The dataset specifies a 'sparse' data description;
     * in order to generate dense vectors from it, we first
     * have to determine the minimum (= 0) and maximum column
     * value to create equal size vectors
     */
    val size = sc.broadcast((dataset.map(_._2).max + 1).toInt)
    
    dataset.groupBy(x => x._1).map(x => {
      
      val row = x._1
      /*
       * The label is a denormalized value and is assigned to
       * each column specific dataset as well; this implies
       * that we only need this value once
       */
      val label = x._2.head._3
      val features = Array.fill[Double](size.value)(0)
      
      val data = x._2.map(v => (v._2.toInt,v._4)).toSeq.sortBy(v => v._1)
      data.foreach(x => features(x._1) = x._2)
      
      new LabeledPoint(row,label,features)
      
    })
 
  }

}
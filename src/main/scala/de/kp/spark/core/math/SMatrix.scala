package de.kp.spark.core.math
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

import scala.collection.mutable.{ArrayBuffer,ArrayBuilder}

/**
 * SMatrix represents an optimized data structure for
 * a quadratic and symmetric matrix, where the diagonal
 * values are fixed
 */
class SMatrix(dim:Int,diag:Double) extends Serializable {

  protected val table = Array.tabulate(dim)(row => Array.fill[Double](dim-row)(0))

  /**
   * This method requires `row`  < `col` values 
   */
  def set(row:Int,col:Int,valu:Double) {

    val x = row
    val y = col - (x+1)

    table(x)(y) = valu
  
  }

  /**
   * This method retrieves a single data point from the
   * symmetric matrix; as the associated table does not
   * provide values for each (row,col), the respective
   * must be computed under the assumption that the matrix
   * is quadratic and symmetric
   */
  def get(row:Int,col:Int):Double = {    
 
    if (row == col) {
      diag
    
    } else if (row < col) {
      
      val x = row
      val y = col - (x+1)
      
       table(x)(y)
      
    } else {
      
      val x = col
      val y = row - (x+1)

      table(x)(y)
      
    }

  }
  /**
   * This method is a convenience method to retrieve
   * values of a complete row from a quadratic and
   * symmetric matrix
   */
  def getRow(row:Int):Array[Double] = {
    
    val values = Array.fill[Double](dim)(0)
    (0 until dim).foreach(col => {
      values(col) = get(row,col)
    })
    
    values
    
  }
  /**
   * This method determines the 'k' most similar
   * items with respect to the row (item)
   */
  def getHighest(row:Int,num:Int):List[(Int,Double)] = {
    
    val values = (0 until dim).map(col => (col,get(row,col))).toList
    val sorted = values.sortBy(x => -x._2)
    /*
     * The highest similarity is '1' and must be excluded
     * as this describes the similarity of row to itself
     */
    sorted.tail.take(num)

  }
  
  /**
   * Returns the K-means cost of a given point against the given cluster centers.
   * The `cost` is defined as 1 - sim
   */
  def pointCost(centers:Array[Int],point:Int): Double = {
    1 - findClosest(centers, point)._2
  }

  def findCosts(points:Array[Int]):Array[Double] = {
    
    val count = points.size    
    val costs = Array.fill[Double](count)(0)
   
    (0 until count).foreach(i => {

      val row = points(i)
      (0 until count).foreach(j => {

        val col = points(j)
        /*
         * Calculate mean value of cost for a certain point
         * for all points in the cluster
         */
        costs(i) = points.map(point => 1 - get(row,col)).sum / count            
          
      })
        
    })

    costs
    
  }
  
  /**
   * Returns the index of the closest center to the given itemset, as well as the
   * similarity to this center
   */
  def findClosest(centers:Array[Int], point:Int): (Int, Double) = {
    
    val similarities = centers.map(center => get(center,point)).toSeq
    
    val max = similarities.max
    val pos = similarities.indexOf(max)

    (pos, max)
  
  }
  
  def serialize():ArrayBuffer[String] = {
		
    val output = ArrayBuffer.empty[String]		
    (0 until dim).foreach(row => output += serializeRow(row))
	
    output
	
  }
  
  def deserializeRow(data:String) {
    
    val Array(sid,seq) = data.split("\\|")   
    table(sid.toInt) = seq.split(",").map(_.toDouble)
    
  }
  
  def serializeRow(row:Int):String = "" + row + "|" + table(row).mkString(",")

}
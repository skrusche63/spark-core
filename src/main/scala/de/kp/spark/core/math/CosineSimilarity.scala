package de.kp.spark.core.math
/* Copyright (c) 2014 Dr. Krusche & Partner PartG
* 
* This file is part of the Spark-Core project
* (https://github.com/skrusche63/spark-cluster).
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

object CosineSimilarity {
  
  /**
   * Method to calculate cosine similarity for Integer vectors
   */
  def compute(x:Array[Int],y:Array[Int]):Double = {

    require(x.size == y.size)
    dotProduct(x, y)/(magnitude(x) * magnitude(y))
  
  }
  /**
   * Method to calculate cosine similarity for Double vectors
   */  
  def compute(x:Array[Double],y:Array[Double]):Double = {

    require(x.size == y.size)
    dotProduct(x, y)/(magnitude(x) * magnitude(y))
  
  }
  
  /**
   * Method to calculate dot product for Integer vectors
   */
  private def dotProduct(x:Array[Int],y:Array[Int]):Int = x.zip(y).map(e => e._1 * e._2).sum 
  /**
   * Method to calculate dot product for Double vectors
   */    
  private def dotProduct(x:Array[Double],y:Array[Double]):Double = x.zip(y).map(e => e._1 * e._2).sum 
 
  /**
   * Method to calculate magnitude from Integers
   */
  private def magnitude(x:Array[Int]): Double = {
    
    val sqrt = x.map(x => x*x)
    math.sqrt(sqrt.sum)

  }
  /**
   * Method to clacluate magnitude from Doubles
   */
  private def magnitude(x:Array[Double]): Double = {
    
    val sqrt = x.map(x => x*x)
    math.sqrt(sqrt.sum)

  }
 
}
package de.kp.spark.core.model
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

class BaseTopics {

  val AMOUNT:String = "amount"
  val EVENT:String  = "event"

  val ITEM:String    = "item"
  val FEATURE:String = "feature"

  val PRODUCT:String = "product"
  val RULE:String    = "rule"

  /*
   * Exceptions: topics may be described in a different manner from
   * an external viewpoint; these external topics are listed below
   * 
   */
  val SEQUENCE:String = "sequence"
    
  private val topics = List(AMOUNT,EVENT,ITEM,FEATURE,PRODUCT,RULE)
  
  def get(candidate:String):String = {
    
    if (topics.contains(candidate)) return candidate
    /* Handle exceptions */
    
    if (candidate == SEQUENCE) return PRODUCT
    
    throw new Exception("Topic is unknown.")
    
  }
  
}
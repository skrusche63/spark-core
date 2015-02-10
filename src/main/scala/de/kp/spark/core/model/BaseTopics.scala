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

  val EVENT:String = "event"
  val ITEM:String  = "item"

  val POINT:String = "point"
    
  val RULE:String = "rule"
  val SEQUENCE:String = "sequence"
    
  val STATE:String = "state"
  val VECTOR:String = "vector"
    
  private val topics = List(EVENT,ITEM,POINT,RULE,SEQUENCE,STATE,VECTOR)
  
  def get(topic:String):String = {
    
    if (topics.contains(topic)) return topic    
    throw new Exception("Unknown topic.")
    
  }
  
}
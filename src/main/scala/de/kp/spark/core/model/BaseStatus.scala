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

class BaseStatus {

  private val BUILDING_PREFIX = "predictive-works:building:"
    
  val BUILDING_STARTED:String  = BUILDING_PREFIX + "started"
  val BUILDING_FINISHED:String = BUILDING_PREFIX + "finished"
    
  val BUILDING_NOT_FINISHED:String = BUILDING_PREFIX + "not:finished"  

  private val MINING_PREFIX = "predictive-works:mining:"
    
  val MINING_STARTED:String  = MINING_PREFIX + "started"
  val MINING_FINISHED:String = MINING_PREFIX + "finished"
    
  val MINING_NOT_FINISHED:String = MINING_PREFIX + "not:finished"  
    
  val TRACKING_STARTED:String = "predictive-works:tracking:started"
  val TRACKING_FINISHED:String = "predictive-works:tracking:finsihed"

  private val TRAINING_PREFIX = "predictive-works:training:"
    
  val TRAINING_STARTED:String  = TRAINING_PREFIX + "started"
  val TRAINING_FINISHED:String = TRAINING_PREFIX + "finished"
    
  val TRAINING_NOT_FINISHED:String = TRAINING_PREFIX + "not:finished"  
  
  val FAILURE:String = "predictive-works:failure"
  val SUCCESS:String = "predictive-works:success"
 
}
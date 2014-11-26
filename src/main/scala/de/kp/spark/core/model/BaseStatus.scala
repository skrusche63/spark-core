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

  val MINING_STARTED:String  = "predictive-works:mining:started"
  val MINING_FINISHED:String = "predictive-works:mining:finished"
    
  val TRACKING_STARTED:String = "predictive-works:tracking:started"
  val TRACKING_FINISHED:String = "predictive-works:tracking:finsihed"
  
  val FAILURE:String = "predictive-works:failure"
  val SUCCESS:String = "predictive-works:success"
 
}
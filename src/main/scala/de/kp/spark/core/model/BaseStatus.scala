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

  /**
   * General purpose stati to describe a building process
   */
  private val BUILDING_PREFIX = "predictive-works:building:"
    
  val BUILDING_STARTED:String  = BUILDING_PREFIX + "started"
  val BUILDING_FINISHED:String = BUILDING_PREFIX + "finished"
    
  val BUILDING_NOT_FINISHED:String = BUILDING_PREFIX + "not:finished"  
  
  /**
   * Stati to describe the data preparation process
   */
  private val PREPARATION_PREFIX = "predictive-works:preparation:"
    
  val PREPARATION_STARTED:String  = PREPARATION_STARTED + "started"
  val PREPARATION_FINISHED:String = PREPARATION_STARTED + "finished"
    
  val PREPARATION_NOT_FINISHED:String = PREPARATION_STARTED + "not:finished"  

  /**
   * Stati to describe the rating building process
   */
  private val RATING_BUILDING_PREFIX = BUILDING_PREFIX + "rating:"
    
  val RATING_BUILDING_STARTED:String  = RATING_BUILDING_PREFIX + "started"
  val RATING_BUILDING_FINISHED:String = RATING_BUILDING_PREFIX + "finished"
    
  val RATING_BUILDING_NOT_FINISHED:String = RATING_BUILDING_PREFIX + "not:finished"  

  /**
   * General purpose stati to describe a mining process
   */
  private val MINING_PREFIX = "predictive-works:mining:"
    
  val MINING_STARTED:String  = MINING_PREFIX + "started"
  val MINING_FINISHED:String = MINING_PREFIX + "finished"
    
  val MINING_NOT_FINISHED:String = MINING_PREFIX + "not:finished"  

  /**
   * General purpose stati to describe a tracking process
   */
    
  val TRACKING_STARTED:String = "predictive-works:tracking:started"
  val TRACKING_FINISHED:String = "predictive-works:tracking:finsihed"

  /**
   * General purpose stati to describe a training process
   */

  private val TRAINING_PREFIX = "predictive-works:training:"
    
  val TRAINING_STARTED:String  = TRAINING_PREFIX + "started"
  val TRAINING_FINISHED:String = TRAINING_PREFIX + "finished"
    
  val TRAINING_NOT_FINISHED:String = TRAINING_PREFIX + "not:finished"  

  /**
   * Stati to describe the feature training process
   */
  private val FEATURE_TRAINING_PREFIX = TRAINING_PREFIX + "feature:"
    
  val FEATURE_TRAINING_STARTED:String  = FEATURE_TRAINING_PREFIX + "started"
  val FEATURE_TRAINING_FINISHED:String = FEATURE_TRAINING_PREFIX + "finished"
    
  val FEATURE_TRAINING_NOT_FINISHED:String = FEATURE_TRAINING_PREFIX + "not:finished"  

  /**
   * Stati to describe the matrix training process
   */
  private val MATRIX_TRAINING_PREFIX = TRAINING_PREFIX + "matrix:"
    
  val MATRIX_TRAINING_STARTED:String  = MATRIX_TRAINING_PREFIX + "started"
  val MATRIX_TRAINING_FINISHED:String = MATRIX_TRAINING_PREFIX + "finished"
    
  val MATRIX_TRAINING_NOT_FINISHED:String = MATRIX_TRAINING_PREFIX + "not:finished"  

  /**
   * Stati to describe the model training process
   */
  private val MODEL_TRAINING_PREFIX = TRAINING_PREFIX + "model:"
    
  val MODEL_TRAINING_STARTED:String  = MODEL_TRAINING_PREFIX + "started"
  val MODEL_TRAINING_FINISHED:String = MODEL_TRAINING_PREFIX + "finished"
    
  val MODEL_TRAINING_NOT_FINISHED:String = MODEL_TRAINING_PREFIX + "not:finished"  
  
  /**
   * General purpose stati to support request - response
   */
  val FAILURE:String = "predictive-works:failure"
  val SUCCESS:String = "predictive-works:success"
 
}
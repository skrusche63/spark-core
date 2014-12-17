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

/**
 * BaseMessages comprises common response messages that are
 * used by the different predictive engines of Predictiveworks.
 */
class BaseMessages {

  def ALGORITHM_IS_UNKNOWN(uid:String,algorithm:String):String = 
    String.format("""[UID: %s] Algorithm '%s' is unknown.""", uid, algorithm)

  def FILE_SINK_REQUIRED(uid:String):String = 
    String.format("""[UID: %s] A file sink is required for this request.""", uid)

  def GENERAL_ERROR(uid:String):String = 
    String.format("""[UID: %s] A general error occurred.""", uid)
 
  def NO_ALGORITHM_PROVIDED(uid:String):String = 
    String.format("""[UID: %s] No algorithm specified.""", uid)

  def NO_PARAMETERS_PROVIDED(uid:String):String = 
    String.format("""[UID: %s] No parameters provided.""", uid)

  def NO_SINK_PROVIDED(uid:String):String = 
    String.format("""[UID: %s] No sink provided.""", uid)

  def NO_SOURCE_PROVIDED(uid:String):String = 
    String.format("""[UID: %s] No source provided.""", uid)

  def REQUEST_IS_UNKNOWN():String = String.format("""Unknown request.""")

  def RULES_DO_NOT_EXIST(uid:String):String = 
    String.format("""[UID: %s] No association rules found.""", uid)

  def SEARCH_INDEX_CREATED(uid:String):String = 
    String.format("""[UID: %s] Search index created.""", uid)

  def SINK_IS_UNKNOWN(uid:String,sink:String):String = 
    String.format("""[UID: %s] Data sink '%s' is unknown.""", uid, sink)

  def SOURCE_IS_UNKNOWN(uid:String,source:String):String = 
    String.format("""[UID: %s] Data source '%s' is unknown.""", uid, source)

  def TASK_ALREADY_STARTED(uid:String):String = 
    String.format("""[UID: %s] The task is already started.""", uid)

  def TASK_DOES_NOT_EXIST(uid:String):String = 
    String.format("""[UID: %s] The task does not exist.""", uid)

  def TASK_IS_UNKNOWN(uid:String,task:String):String = 
    String.format("""[UID: %s] The task '%s' is unknown.""", uid, task)
 
  def TRACKED_DATA_RECEIVED(uid:String):String = 
    String.format("""[UID: %s] Tracked data received.""", uid)

  def TRAINING_NOT_FINISHED(uid:String):String = 
    String.format("""[UID: %s] Training task is not finished yet.""", uid)
 
}
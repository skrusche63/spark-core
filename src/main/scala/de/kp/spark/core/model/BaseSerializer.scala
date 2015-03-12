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

import org.json4s._

import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read,write}

class BaseSerializer {
    
  implicit val formats = Serialization.formats(NoTypeHints)
  /*
   * Serialization and de-serialization of behaviors; a behavior
   * describes an ordered sequence of behavioral states on a per
   * site and user basis
   */
  def serializeBehaviors(behaviors:Behaviors):String = write(behaviors)
  def deserializeBehavior(behaviors:String):Behaviors = read[Behaviors](behaviors)
  /*
   * Serialization and de-serialization of clustered points and 
   * sequences is used to support model persistence in Similarity 
   * Analysis 
   */
  def serializeClusteredPoints(points:ClusteredPoints):String = write(points)
  def deserializeClusteredPoints(points:String):ClusteredPoints = read[ClusteredPoints](points)

  def serializeClusteredSequences(sequences:ClusteredSequences):String = write(sequences)
  def deserializeClusteredSequences(sequences:String):ClusteredSequences = read[ClusteredSequences](sequences)
  
  /*
   * Serialization and de-serialization of field or metadata
   * specification that describe the mapping from external
   * data source fields to internal pre-defined variables
   */
  def serializeFields(fields:Fields):String = write(fields) 
  def deserializeFields(fields:String):Fields = read[Fields](fields)
  /*
   * Serialization and de-serialization of Markov rules, where
   * a certain state is described by a name and a probability to
   * reach this state from a previous state. Markovian rules
   * are used by Intent Recognition
   */
  def serializeMarkovRules(rules:MarkovRules):String = write(rules) 
  def deserializeMarkovRules(rules:String):MarkovRules = read[MarkovRules](rules)
  /*
   * Serialization and de-serialization of Markov outliers;
   * this data structure is used by Outlier Detection
   */
  def serializeOutliers(outliers:Outliers):String = write(outliers)
  def deserializeBOutliers(outliers:String):Outliers = read[Outliers](outliers)

  /*
   * Serialization and de-serialization of model parameters
   * used to build or train a specific model; these parameters
   * refer to a certain task (uid) and model name (name)
   */
  def serializeParams(params:Params):String = write(params) 
  def deserializeParams(params:String):Params = read[Params](params)
    
  /*
   * Support for serialization and deserialization of patterns
   */
  def serializePatterns(patterns:Patterns):String = write(patterns)  
  def deserializePatterns(patterns:String):Patterns = read[Patterns](patterns)

  def deserializeResponse(response:String):ServiceResponse = read[ServiceResponse](response)
  def serializeResponse(response:ServiceResponse):String = write(response)
  
  def deserializeRequest(request:String):ServiceRequest = read[ServiceRequest](request)
  def serializeRequest(request:ServiceRequest):String = write(request)
  /*
   * Serialization and de-serialization support for association rules;
   * a serialized version is e.g. stored in a Redis instance or sent
   * via TCP to a remote Akka service
   */  
  def serializeRules(rules:Rules):String = write(rules)  
  def deserializeRules(rules:String):Rules = read[Rules](rules)
  /*
   * Serialization and de-serialization support for classifier
   * rules; these rules can be used to feed to e.g. decision trees
   */
  def serializeCRules(rules:CRules):String = write(rules)
  def deserializeCRules(rules:String):CRules = read[CRules](rules)

  def serializeStatus(status:Status):String = write(status)
  def deserializeStatus(status:String):Status = read[Status](status)

  def serializeStatusList(statuses:StatusList):String = write(statuses)
  def deserializeStatusList(statuses:String):StatusList = read[StatusList](statuses)

}
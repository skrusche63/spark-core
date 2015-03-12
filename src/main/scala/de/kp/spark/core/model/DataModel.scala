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
 * The Field and Fields classes are used to specify the fields with
 * respect to the data source provided, that have to be mapped onto
 * site,timestamp,user,group,item
 */

/********** REGISTRATION SUPPORT **/

case class AliveMessage()
/**
 * Status event specifies a message sent by the Supervisor actor 
 * to indicate that a certain 'status' of a data mining or model 
 * building task has been reached.
 */
case class StatusEvent(uid:String,service:String,task:String,value:String)

case class Field(
  name:String,datatype:String,value:String
)
case class Fields(items:List[Field])

/********* TRAINING SUPPORT */

/**
 * Param & Params are used to register the model parameters
 * used for a certain data mining or model building task
 */
case class Param(
  name:String,datatype:String,value:String
)
case class Params(items:List[Param])

/********* LISTENER SUPPORT */

/**
 * Listener specifies a remote akka service to send
 * notifications to
 */
case class Listener(
  timeout:Int, url:String
)

/********* ASSOCIATION SUPPORT */

/**
 * A Rule specifies the raw result of association rule mining, and describes
 * the relation between 'antecedent' & 'consequent' itemsets.
 */
case class Rule (
  /* 
   * 'total' specifies the total number of transactions and is added 
   * to provide a reference base for the 'support' parameter 
   */
  antecedent:List[Int],consequent:List[Int],support:Int,total:Long,confidence:Double
)
  
case class Rules(items:List[Rule])

/**
 * A CRule is derived from mined association rules and has a focus on a 
 * single consequent, and the respective weight of this with respective
 * to the association rule.
 * 
 * A CRule is the basis for associative classifiers, where the consequent
 * is used as the target class to classify the items specified as antecedent.
 * 
 */
case class CRule(
  antecedent:List[Int],consequent:Int,support:Int,confidence:Double,weight:Double
)  

case class CRules(items:List[CRule])

/********* CLUSTER SUPPORT */

/**
 * A LabeledPoint describes a combination of a feature
 * vector and an assigned label. Each data record is
 * also uniquely identifier by the 'id' parameter.
 * 
 * This parameter is usually equal to the row descriptor
 * of the data record (see vector description).
 * 
 */
case class LabeledPoint(
  id:Long,label:String,features:Array[Double]
)

/********* INTENT SUPPORT */
/*
 * Behavior assigns a set of (time-ordered) states to a certain 
 * site and user; a state is an aggregated part of information
 * that must be derived from e.g. commerce transactions
 */
case class Behavior(site:String,user:String,states:List[String])
case class Behaviors(items:List[Behavior])

case class MarkovState(name:String,probability:Double)
/*
 * A Markov Rule assigns a certain state (antecedent) to a list
 * of most probable subsequent states
 */
case class MarkovRule(antecedent:String,consequent:List[MarkovState])

case class MarkovRules(items:List[MarkovRule])

case class NumberedSequence(sid:Int,data:Array[Array[Int]])

/********* SERIES SUPPORT */

case class Pattern(support:Int,itemsets:List[List[Int]])
case class Patterns(items:List[Pattern])

/********* SIMILARITY SUPPORT */

case class ClusteredPoint(
  cluster:Int,distance:Double,point:LabeledPoint
)

case class ClusteredPoints(items:List[ClusteredPoint])

case class ClusteredSequence(
  cluster:Int,similarity:Double,sequence:NumberedSequence
)

case class ClusteredSequences(items:List[ClusteredSequence])

/********* REQUEST / RESPONSE SUPPORT */

/**
 * ServiceRequest & ServiceResponse specify the content sent to 
 * and received from the different predictive engines
 */
case class ServiceRequest(
  service:String,task:String,data:Map[String,String]
)
case class ServiceResponse(
  service:String,task:String,data:Map[String,String],status:String
)

/**
 * Service requests are mapped onto status descriptions 
 * and are stored in a Redis instance
 */
case class Status(
  service:String,task:String,value:String,timestamp:Long
)

case class StatusList(items:List[Status])

/**
 * IO DATA MODEL
 */
case class AmountObject(
  val site:String,
  val user:String,
  val timestamp:Long,
  val amount:Float  
)

case class EventScoreObject(
  val site:String,
  val user:String,
  val item:String,
  val score:Double,
  val timestamp:Long,
  val event:Int  
)

case class ItemObject(
  val site:String,
  val timestamp:Long,
  val user:String,
  val group:String,
  val item:Integer,
  val score:Double
)

case class ItemScoreObject(
  val site:String,
  val user:String,
  val item:Integer,
  val score:Double
)

case class RuleObject(
  val uid:String,
  val timestamp:Long,
  val antecendent:Seq[Int],
  val consequent:Seq[Int],
  val support:Int,
  val total:Long,
  val confidence:Double
)

case class TargetedPointObject(
  val target:Double,features:Seq[Double]
)
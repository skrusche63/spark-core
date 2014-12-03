package de.kp.spark.core
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

object Names {

  /********************************************************
   *              ELASTIC SEARCH FOR HADOOP
   *******************************************************/

  val ES_QUERY:String = "es.query"
  val ES_RESOURCE:String = "es.resource"

  /********************************************************
   *                     INDEXING
   *******************************************************/

  val AMOUNT_FIELD:String = "amount"

  val ANTECEDENT_FIELD:String = "antecedent"
  val CONSEQUENT_FIELD:String = "consequent"
 
  val CONFIDENCE_FIELD:String = "confidence"

  val EVENT_FIELD:String = "event"
  val GROUP_FIELD:String = "group"

  val ITEM_FIELD:String  = "item"
  val PRICE_FIELD:String = "price"
  /*
   * This is a relative identifier with respect to the timestamp
   * to specify which antecendents refer to the same association
   * rule
   */
  val RULE_FIELD:String = "rule"
    
  val SITE_FIELD:String = "site"

  val SUPPORT_FIELD:String = "support"
  val TIMESTAMP_FIELD:String = "timestamp"
  /*
   * The unique identifier of the mining task that created the
   * respective rules
   */
  val UID_FIELD:String = "uid"  
  val USER_FIELD:String = "user"
  
  val WEIGHT_FIELD:String = "weight"
   
  /********************************************************
   *                     REQUEST
   *******************************************************/
  
  val REQ_ALGORITHM:String = "algorithm"
  
  val REQ_COLUMNS:String = "columns"
  val REQ_CONTEXT:String = "context"

  val REQ_END:String = "end"

  val REQ_FEATURES:String = "features"

  val REQ_FIELD:String  = "field"
  val REQ_FIELDS:String = "fields"

  val REQ_FORMAT:String = "format"

  val REQ_ITEM:String  = "item"
  val REQ_ITEMS:String = "items"

  val REQ_NAME:String  = "name"
  val REQ_NAMES:String = "names"

  val REQ_QUERY:String = "query"
    
  val REQ_SINK:String   = "sink"
  val REQ_SOURCE:String = "source"
    
  val REQ_SITE:String  = "site"
  val REQ_START:String = "start"

  val REQ_TOTAL:String = "total"
  val REQ_TYPES:String = "types"
    
  val REQ_UID:String   = "uid"
    
  val REQ_USER:String  = "user"
  val REQ_USERS:String = "users"

  val REQ_SOURCE_INDEX:String = "source.index"
  val REQ_SOURCE_TYPE:String  = "source.type"

}
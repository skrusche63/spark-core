package de.kp.spark.core.elastic
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

import org.elasticsearch.common.xcontent.{XContentBuilder,XContentFactory}

import de.kp.spark.core.Names

import scala.collection.JavaConversions._
import scala.collection.mutable.HashMap

class ElasticItemBuilder {

  import de.kp.spark.core.Names._
  
  def createBuilder(mapping:String):XContentBuilder = {
    /*
     * Define mapping schema for index 'index' and 'type'; the schema 
     * is used by the association, recommendation, series and other
     * engines
     */
    val builder = XContentFactory.jsonBuilder()
          .startObject()
            .startObject(mapping)
              .startObject("properties")

                /* uid */
                .startObject(UID_FIELD)
                  .field("type", "string")
                  .field("index", "not_analyzed")
                .endObject()
                    
                /* site */
                .startObject(SITE_FIELD)
                   .field("type", "string")
                   .field("index", "not_analyzed")
                .endObject()

                /* user */
                .startObject(USER_FIELD)
                   .field("type", "string")
                   .field("index", "not_analyzed")
                .endObject()//

                /* timestamp */
                .startObject(TIMESTAMP_FIELD)
                  .field("type", "long")
                .endObject()

                /* group */
                .startObject(GROUP_FIELD)
                   .field("type", "string")
                   .field("index", "not_analyzed")
                .endObject()//

                /* item */
                .startObject(ITEM_FIELD)
                   .field("type", "integer")
                .endObject()

                /* score */
                .startObject(SCORE_FIELD)
                   .field("type", "double")
                .endObject()

              .endObject() // properties
            .endObject()   // mapping
          .endObject()
                    
    builder

  }
 
  def createSource(params:Map[String,String]):java.util.Map[String,Object] = {
    
    val source = HashMap.empty[String,String]
    
    source += Names.SITE_FIELD -> params(Names.SITE_FIELD)
    source += Names.USER_FIELD -> params(Names.USER_FIELD)
      
    source += Names.TIMESTAMP_FIELD -> params(Names.TIMESTAMP_FIELD) 
    source += Names.GROUP_FIELD -> params(Names.GROUP_FIELD)
 
    source
    
  }
  
  def createSourceJSON(params:Map[String,String]):List[XContentBuilder] = {
    
    /*
     * The 'item' field specifies a comma-separated list
     * of item (e.g.) product identifiers. Note, that every
     * item is actually indexed individually. This is due to
     * synergy effects with other data sources
     */
    val items = params(Names.ITEM_FIELD).split(",")
    /*
     * A trackable event may have a 'score' field assigned;
     * note, that this field is optional
     */
    val scores = if (params.contains(Names.REQ_SCORE)) params(Names.REQ_SCORE).split(",").map(_.toDouble) else Array.fill[Double](items.length)(0)

    val zipped = items.zip(scores)
    
    /* Common field values */
    val uid = params(Names.UID_FIELD)

    val site = params(Names.SITE_FIELD)
    val user = params(Names.USER_FIELD)
     
    val timestamp = params(Names.TIMESTAMP_FIELD).toLong 
    val group = params(Names.GROUP_FIELD)

    zipped.map(x => {
      
      val (item,score) = x

      val builder = XContentFactory.jsonBuilder()
	  builder.startObject()
	  
	  builder.field(Names.UID_FIELD, uid)

	  builder.field(Names.SITE_FIELD, site)
	  builder.field(Names.USER_FIELD, user)

	  builder.field(Names.TIMESTAMP_FIELD, timestamp)
	  builder.field(Names.GROUP_FIELD, group)

	  builder.field(Names.ITEM_FIELD, item)
	  builder.field(Names.SCORE_FIELD, score)
	  
      builder.endObject()
	  
      builder
    
    }).toList
    
  }

}
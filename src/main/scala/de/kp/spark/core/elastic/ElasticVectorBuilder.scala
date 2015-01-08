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

class ElasticVectorBuilder {

  import de.kp.spark.core.Names._
  
  def createBuilder(mapping:String):XContentBuilder = {

    val builder = XContentFactory.jsonBuilder()
          .startObject()
            .startObject(mapping)
              .startObject("properties")

                /* row */
                .startObject(ROW_FIELD)
                  .field("type", "long")
                  .field("index", "not_analyzed")
                .endObject()
                    
                /* col */
                .startObject(COL_FIELD)
                   .field("type", "long")
                   .field("index", "not_analyzed")
                .endObject()

                /* val */
                .startObject(VAL_FIELD)
                   .field("type", "string")
                   .field("index", "not_analyzed")
                .endObject()

              .endObject() // properties
            .endObject()   // mapping
          .endObject()
                    
    builder

  }
  
  def createSourceJSON(params:Map[String,String]):XContentBuilder = {
    
    val row = params(Names.ROW_FIELD).toLong
    val col = params(Names.COL_FIELD).toLong
    
    val value = params(Names.VAL_FIELD)

    val builder = XContentFactory.jsonBuilder()
	builder.startObject()
	  
	builder.field(Names.ROW_FIELD, row)
    builder.field(Names.COL_FIELD, col)

    builder.field(Names.VAL_FIELD, value)
	  
    builder.endObject()
    builder
    
  }
}
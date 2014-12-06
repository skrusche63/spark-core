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

class ElasticFeatureBuilder {

  import de.kp.spark.core.Names._
  
  def createBuilder(mapping:String,names:List[String],types:List[String]):XContentBuilder = {
    /*
     * Define mapping schema for index 'index' and 'type'
     */
    val builder = XContentFactory.jsonBuilder()
    builder
      .startObject()
        .startObject(mapping)
          .startObject("properties")

            /* timestamp */
            .startObject(TIMESTAMP_FIELD)
              .field("type", "long")
            .endObject()
                    
            /* site */
            .startObject(SITE_FIELD)
              .field("type", "string")
              .field("index", "not_analyzed")
            .endObject()

    (0 until names.length).foreach( i => {
      builder
        .startObject(names(i))
          .field("type",types(i))
        .endObject()

    })

    builder
          .endObject() // properties
        .endObject()   // mapping
      .endObject()
                    
    builder

  }
 
  def createSource(params:Map[String,String]):java.util.Map[String,Object] = {
    
    val now = new java.util.Date()
    val source = HashMap.empty[String,String]
    
    source += Names.SITE_FIELD -> params(Names.SITE_FIELD)
    source += Names.TIMESTAMP_FIELD -> now.getTime().toString    
 
    /* 
     * Restrict parameters to those that are relevant to feature description;
     * note, that we use a flat JSON data structure for simplicity and distinguish
     * field semantics by different prefixes 
     */
    val records = params.filter(kv => kv._1.startsWith("lbl.") || kv._1.startsWith("fea."))
    for (rec <- records) {
      
      val (k,v) = rec
        
      val name = k.replace("lbl.","").replace("fea.","")
      source += k -> v      
      
    }

    source
    
  }

}
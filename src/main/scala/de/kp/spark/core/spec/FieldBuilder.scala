package de.kp.spark.core.spec
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

import de.kp.spark.core.Names._
import de.kp.spark.core.model._

import scala.collection.mutable.ArrayBuffer

class FieldBuilder {

  def build(req:ServiceRequest,topic:String):List[Field] = {
                
    val fields = ArrayBuffer.empty[Field]
    topic match {
      
      case "amount" => {

        val names = List(SITE_FIELD,TIMESTAMP_FIELD,USER_FIELD,AMOUNT_FIELD)
        val types = List("string","long","string","float")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (req.data.contains(name)) req.data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }
      case "event" => {

        val names = List(SITE_FIELD,TIMESTAMP_FIELD,USER_FIELD,EVENT_FIELD,ITEM_FIELD,SCORE_FIELD)
        val types = List("string","long","string","integer","integer","double")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (req.data.contains(name)) req.data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList

      }

      case "item" => {

        val names = List(SITE_FIELD,TIMESTAMP_FIELD,USER_FIELD,GROUP_FIELD,ITEM_FIELD,SCORE_FIELD)
        val types = List("string","long","string","string","integer","double")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (req.data.contains(name)) req.data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }
      case "product" => {

        val names = List(SITE_FIELD,TIMESTAMP_FIELD,USER_FIELD,GROUP_FIELD,ITEM_FIELD,PRICE_FIELD)
        val types = List("string","long","string","string","integer","float")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (req.data.contains(name)) req.data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }
      
      case _ => fields.toList 
      
    }
  
  }

}
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

  def build(req:ServiceRequest,topic:String):List[Field] = build(req.data,topic)

  def build(data:Map[String,String],topic:String):List[Field] = {

    /*
     * Note, that the 'site' field is fixed and cannot be renamed; it specifies
     * the API KEY of a certain tenant
     */
    val fields = ArrayBuffer.empty[Field]
    fields += new Field(SITE_FIELD,"string",SITE_FIELD)
    
    topic match {
      
      case "amount" => {

        val names = List(TIMESTAMP_FIELD,USER_FIELD,AMOUNT_FIELD)
        val types = List("long","string","float")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (data.contains(name)) data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }
      case "event" => {

        val names = List(TIMESTAMP_FIELD,USER_FIELD,EVENT_FIELD,ITEM_FIELD,SCORE_FIELD)
        val types = List("long","string","integer","integer","double")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (data.contains(name)) data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList

      }

      case "item" => {

        val names = List(TIMESTAMP_FIELD,USER_FIELD,GROUP_FIELD,ITEM_FIELD,SCORE_FIELD)
        val types = List("long","string","string","integer","double")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (data.contains(name)) data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }
      case "product" => {

        val names = List(TIMESTAMP_FIELD,USER_FIELD,GROUP_FIELD,ITEM_FIELD,PRICE_FIELD)
        val types = List("long","string","string","integer","float")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (data.contains(name)) data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }

      case "sequence" => {

        val names = List(TIMESTAMP_FIELD,USER_FIELD,GROUP_FIELD,ITEM_FIELD)
        val types = List("long","string","string","integer")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (data.contains(name)) data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }

      case "state" => {

        val names = List(TIMESTAMP_FIELD,USER_FIELD,STATE_FIELD)
        val types = List("long","string","string")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (data.contains(name)) data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }

      case "vector" => {

        val names = List(ROW_FIELD,COL_FIELD,LBL_FIELD,VAL_FIELD)
        val types = List("long","long","string","string")
        
        names.zip(types).foreach(entry => {
          
          val (name,datatype) = entry
          val value = if (data.contains(name)) data(name) else name
          
          fields += new Field(name,datatype,value)
          
        })

        fields.toList
        
      }
      
      case _ => fields.toList 
      
    }
  
  }

}
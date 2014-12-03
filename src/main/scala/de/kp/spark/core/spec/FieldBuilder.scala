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
      
      case "item" => {

        fields += new Field(SITE_FIELD,"string",req.data(SITE_FIELD))
        fields += new Field(TIMESTAMP_FIELD,"long",req.data(TIMESTAMP_FIELD))

        fields += new Field(USER_FIELD,"string",req.data(USER_FIELD))
        fields += new Field(GROUP_FIELD,"string",req.data(GROUP_FIELD))

        fields += new Field(ITEM_FIELD,"integer",req.data(ITEM_FIELD))

        fields.toList
        
      }
      
      case _ => fields.toList
      
    }
  
  }

}
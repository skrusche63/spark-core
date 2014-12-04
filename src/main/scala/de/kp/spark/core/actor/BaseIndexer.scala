package de.kp.spark.core.actor
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

import de.kp.spark.core.Configuration

import de.kp.spark.core.Names
import de.kp.spark.core.model._

import de.kp.spark.core.io.ElasticIndexer
import de.kp.spark.core.elastic.{ElasticBuilderFactory => EBF}

class BaseIndexer(config:Configuration) extends RootActor(config) {
  
  def receive = {
    
    case req:ServiceRequest => {

      val uid = req.data(Names.REQ_UID)
      val origin = sender

      try {
    
        val (names,types) = getSpec(req)
 
        val index   = req.data(Names.REQ_INDEX)
        val mapping = req.data(Names.REQ_TYPE)
    
        val topic = getTopic(req)
        
        val builder = EBF.getBuilder(topic,mapping,names,types)
        val indexer = new ElasticIndexer()
    
        indexer.create(index,mapping,builder)
        indexer.close()
      
        val data = Map(Names.REQ_UID -> uid, "message" -> messages.SEARCH_INDEX_CREATED(uid))
        val response = new ServiceResponse(req.service,req.task,data,status.SUCCESS)	
      
        origin ! response
      
      } catch {
        
        case e:Exception => {
          
          log.error(e, e.getMessage())
      
          val data = Map(Names.REQ_UID -> uid, "message" -> e.getMessage())
          val response = new ServiceResponse(req.service,req.task,data,status.FAILURE)	
      
          origin ! response
          
        }
      
      } finally {
        
        context.stop(self)

      }
    
    }
    
  }
  
  protected def getSpec(req:ServiceRequest):(List[String],List[String]) = (List.empty[String],List.empty[String])
  
  protected def getTopic(req:ServiceRequest):String = {
  
    val baseTopics = new BaseTopics()
   
    val candidate = req.task.split(":")(1)
    val topic = baseTopics.get(candidate)
    
    topic
    
  }
  
}
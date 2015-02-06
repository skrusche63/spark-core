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

import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.serializer.KryoSerializer

trait SparkService {
  
  protected def createCtxLocal(name:String, props:Map[String,String]):SparkContext = {

    for (prop <- props) {
      System.setProperty(prop._1,prop._2)      
    }

    val runtime = Runtime.getRuntime()
	runtime.gc()
		
	val cores = runtime.availableProcessors()
		
	val conf = new SparkConf()
	conf.setMaster("local["+cores+"]")
		
	conf.setAppName(name);
    conf.set("spark.serializer", classOf[KryoSerializer].getName)		
    /* 
     * Set the Jetty port to 0 to find a random port
     */
    conf.set("spark.ui.port", "0")        
    
    /*
     * Connection Parameters
     * 
     * - spark.cassandra.connection.host	
     * 
     *   description: contact point to connect to the Cassandra cluster	
     *   default: address of the Spark master host
     *   
     * - spark.cassandra.connection.rpc.port	
     * 
     *   description: Cassandra thrift port	
     *   default: 9160
     *   
     * - spark.cassandra.connection.native.port	
     * 
     *   description: Cassandra native port	
     *   default: 9042
     *   
     * - spark.cassandra.connection.conf.factory	
     * 
     *   description: name of a Scala module or class implementing CassandraConnectionFactory 
     *                providing connections to the Cassandra cluster	
     *   default: com.datastax.spark.connector.cql.DefaultConnectionFactory
     *   
     * - spark.cassandra.connection.keep_alive_ms	
     * 
     *   description: period of time to keep unused connections open	
     *   default: 250 ms
     * 
     * - spark.cassandra.connection.timeout_ms	
     * 
     *   description: maximum period of time to attempt connecting to a node	
     *   default: 5000 ms
     * 
     * - spark.cassandra.connection.reconnection_delay_ms.min	
     * 
     *   description: minimum period of time to wait before reconnecting to a dead node	
     *   default: 1000 ms
     * 
     * - spark.cassandra.connection.reconnection_delay_ms.max	
     * 
     *   description: maximum period of time to wait before reconnecting to a dead node	
     *   default: 60000 ms
     * 
     * - spark.cassandra.connection.local_dc	
     * 
     *   description: the local DC to connect to (other nodes will be ignored)	
     *   default: None
     * 
     * - spark.cassandra.auth.username	
     * 
     *   description: login name for password authentication	
     * 
     * - spark.cassandra.auth.password	
     * 
     *   description: password for password authentication	
     * 
     * - spark.cassandra.auth.conf.factory	
     * 
     *   description: name of a Scala module or class implementing AuthConfFactory providing custom 
     *                authentication configuration	
     *   default com.datastax.spark.connector.cql.DefaultAuthConfFactory
     *   
     * - spark.cassandra.query.retry.count	
     * 
     *   description: number of times to retry a timed-out query	
     *   default: 10
     * 
     * - spark.cassandra.read.timeout_ms	
     * 
     *   definition: maximum period of time to wait for a read to return	
     *   default: 12000 ms
     * 
     * 
     * Parameters that can be used to adjust read operations
     * 
     * - spark.cassandra.input.split.size	
     * 
     *   description: approx number of rows in a Spark partition	
     *   default: 100000
     *   
     * - spark.cassandra.input.page.row.size	
     * 
     *   description: number of rows fetched per roundtrip	
     *   default: 1000
     *   
     * - spark.cassandra.input.consistency.level	
     * 
     *   description: consistency level to use when reading	
     *   default: LOCAL_ONE
     */
	new SparkContext(conf)
		
  }

}
package com.atguigu.sparkstreaming01

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object wordcountbyqueue {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordcount")
    val ssc = new StreamingContext(conf,Seconds(3))
   val queue: mutable.Queue[RDD[Int]] = mutable.Queue[RDD[Int]]()
   val rddStream: InputDStream[Int] = ssc.queueStream(queue,true)
    rddStream.reduce(_+_).print(100)
    ssc.start()

    while (true){
      val rdd: RDD[Int] = ssc.sparkContext.parallelize(1 to 100)
      queue.enqueue(rdd)
      Thread.sleep(1000)
      println(queue.length)
    }
    ssc.awaitTermination()
  }

}

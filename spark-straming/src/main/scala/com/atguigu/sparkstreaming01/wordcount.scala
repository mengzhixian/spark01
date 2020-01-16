package com.atguigu.sparkstreaming01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object wordcount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordcount")
    val ssc = new StreamingContext(conf,Seconds(3))
    val source: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102",9999)
    val unit: DStream[(String, Int)] = source.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    unit.print(100)
    ssc.start()
    ssc.awaitTermination()
  }

}

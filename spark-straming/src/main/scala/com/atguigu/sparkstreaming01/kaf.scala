package com.atguigu.sparkstreaming01


import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils

object kaf {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("a").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
    val params: Map[String, String] = Map[String, String](
      "group.id" -> "0830",
      "bootstrap.servers" -> "hadoop102:9092,hadoop103:9092,hadoop104:9092")
    // 从kafka读取数据
    val sourceStream: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc,
      params,
      Set("132"))
    sourceStream
      .map {
        case (_, v) => v
      }
      .flatMap(_.split("\\W+"))
      .map((_, 1))
      .reduceByKey(_ + _)
      .print(1000)



    ssc.start()

    ssc.awaitTermination()
  }

}

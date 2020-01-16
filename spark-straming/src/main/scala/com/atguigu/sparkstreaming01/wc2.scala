package com.atguigu.sparkstreaming01

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils

object wc2 {
  def sscget()={
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("c")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
    ssc.checkpoint("./ck1")
    val params: Map[String, String] = Map[String, String](
      "group.id" -> "0830",
      "bootstrap.servers" -> "hadoop102:9092,hadoop103:9092,hadoop104:9092")
    val sourceStream: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc,
      params,
      Set("s0830"))

    sourceStream
      .map {
        case (_, v) => v
      }
      .flatMap(_.split("\\W+"))
      .map((_, 1))
      .reduceByKey(_ + _)
      .print(1000)
    ssc
  }
  def main(args: Array[String]): Unit = {

    val ssc= StreamingContext.getOrCreate("./ck1",sscget)

    ssc.start()
    ssc.awaitTermination()
  }
}

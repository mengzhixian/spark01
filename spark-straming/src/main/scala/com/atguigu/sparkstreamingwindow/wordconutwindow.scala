package com.atguigu.sparkstreamingwindow

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object wordconutwindow {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Transform")
    val ssc = new StreamingContext(conf, Seconds(3))

    ssc.checkpoint("./ck7")
    val source= ssc.socketTextStream("hadoop102",9999).window(Seconds(9),Seconds(3))
    source.flatMap(_.split("\\W+")).map((_,1)).reduceByKey(_+_).print()
    ssc.start()

    ssc.awaitTermination()


  }

}

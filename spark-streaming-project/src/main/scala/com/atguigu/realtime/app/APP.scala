package com.atguigu.realtime.app

import com.atguigu.realtime.bean.AdsInfo
import com.atguigu.realtime.util.MyKafkaUtil
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

trait APP {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("App")
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(5))
    ssc.checkpoint("./ck11")

    //从kafka读数据
    val source: DStream[AdsInfo] = MyKafkaUtil.getKafkaStream(ssc, "ads_log1").map(s => {

      val splits: Array[String] = s.split(",")
      AdsInfo(splits(0).toLong, splits(1), splits(2), splits(3), splits(4))

    })



    //操作dstream
    doSomething(source)

    ssc.start()
    ssc.awaitTermination()
  }
  def doSomething(ssc: DStream[AdsInfo]):Unit
}

package com.atguigu.sparkstreaming01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object withoutcountword {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("withoutcountword").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.checkpoint("./ck3")
    val source: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102",9999)
    source.flatMap(_.split("\\W+")).map(((_,1))).updateStateByKey((seq:Seq[Int],op:Option[Int])=>{
      Some(op.getOrElse(0)+seq.sum)
    }).print()
    ssc.start()
    ssc.awaitTermination()

  }

}

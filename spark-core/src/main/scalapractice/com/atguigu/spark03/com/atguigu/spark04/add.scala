package com.atguigu.spark03.com.atguigu.spark04

import org.apache.spark.{SparkConf, SparkContext}

object add {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
         val list1 = List(1,2,3,4,5)
         val rdd1 = sc.makeRDD(list1,2)
    val acc = new myadd
    sc.register(acc,"111")
    rdd1.map(
      x=>{
        acc.add(x)
        x
      }
    ).collect()
    println(acc.value)
    sc.stop()

  }

}

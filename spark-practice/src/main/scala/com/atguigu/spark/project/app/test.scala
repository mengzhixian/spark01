package com.atguigu.spark.project.app

import org.apache.spark.{SparkConf, SparkContext}

object test {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
         val list1 = List(1,2,3)
         val rdd1 = sc.makeRDD(list1,1)
    rdd1.map((_,1)).reduceByKey(_+_).groupByKey().collect()
    Thread.sleep(1000000)
    sc.stop()

  }

}

package com.atguigu.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object mapvalues {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8))
    val rdd1: RDD[(String, Int)] = sc.makeRDD(list1, 2)
    rdd1.groupByKey().mapValues(_.sum).sortByKey().collect().foreach(println)
    sc.stop()
  }

}

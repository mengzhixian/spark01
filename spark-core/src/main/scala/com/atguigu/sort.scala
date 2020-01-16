package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object sort {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SortBy").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    val list1 = List("hello", "abc", "aaa", "abcde")
    val rdd1 = sc.parallelize(list1, 2)
    val rdd2: RDD[String] = rdd1.sortBy((x=>(x.length,x)))
    rdd2.collect().foreach(println)

  }

}

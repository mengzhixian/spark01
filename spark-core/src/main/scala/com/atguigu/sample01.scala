package com.atguigu

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object sample01 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(10,20,30,40)
    val rdd1: RDD[Int] = sc.parallelize(list1,3)
    val rdd2: RDD[Int] = rdd1.sample(false,0.5)
    rdd2.collect().foreach(println)
    sc.stop()
  }
}

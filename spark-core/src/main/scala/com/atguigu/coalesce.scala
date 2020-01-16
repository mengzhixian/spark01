package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object coalesce {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(10,20,30,40)
    val rdd1: RDD[Int] = sc.parallelize(list1,3)
    println(rdd1.getNumPartitions)
    val rdd2: RDD[Int] = rdd1.coalesce(4,true)
    rdd1.repartition(5)
    println(rdd2.getNumPartitions)
//    rdd2.collect().foreach(println)
  }

}

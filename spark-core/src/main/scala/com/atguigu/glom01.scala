package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object glom01 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(10,20,30,40,50,60,70)
    val rdd1: RDD[Int] = sc.parallelize(list1,2)
    val rdd2: RDD[Array[Int]] = rdd1.glom()
    rdd1.collect()foreach(println)
    sc.stop()
//val conf: SparkConf = new SparkConf().setAppName("Glom").setMaster("local[2]")
//    val sc: SparkContext = new SparkContext(conf)
//    val list1 = List(30, 50, 70, 60, 10, 20)
//    val rdd1: RDD[Int] = sc.parallelize(list1, 2)
//
//    val rdd2: RDD[Array[Int]] = rdd1.glom()
//    rdd2.collect.foreach(x => println(x.mkString(",")))
//    sc.stop()
  }

}

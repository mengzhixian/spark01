package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object practice {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val rdd1: RDD[String] = sc.textFile("D:\\agent.log")
    val proadsone = rdd1.map(
      s => {
        val words: Array[String] = s.split(" ")
        ((words(1), words(4)), 1)
      }
    )
    val proadscount: RDD[(String, Iterable[(String, Int)])] = proadsone.reduceByKey(_ + _).map {
      case ((pro, ads), count) => (pro, (ads, count))
    }.groupByKey()
    val result: RDD[(String, List[(String, Int)])] = proadscount.map {
      case (pro, it) => (pro, it.toList.sortWith(_._2>_._2).take(3))
    }
    val result2: RDD[(String, List[(String, Int)])] = result.sortBy(_._1.toInt)
    result2.collect()foreach(println)
    sc.stop()
  }

}

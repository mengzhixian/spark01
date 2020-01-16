package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object spark02 {
  def main(args: Array[String]): Unit = {
   val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("spark02")
    val sc = new SparkContext(conf)
    val ints = List(10,20,30,40,50,30,40)
    val rdd1: RDD[Int] = sc.parallelize(ints)
    val rdd2: RDD[(Int, Int)] = rdd1.mapPartitionsWithIndex(
      (index, it) => it.map(
        it => (index, it)
      )
    )
    rdd2.collect().foreach(println)

  }

}

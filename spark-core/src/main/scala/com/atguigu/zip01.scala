package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object zip01 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(10,20,30,40,50)
    val list2 = List(1,2,3,4)
    val rdd2: RDD[Int] = sc.makeRDD(list2)
    val rdd: RDD[Int] = sc.parallelize(list1)
    val rdd4: RDD[(Int, Int)] = rdd.zip(rdd2)
    val rdd1: RDD[(Int, Long)] = rdd.zipWithIndex()
    val rdd6: RDD[(Int, Int)] = rdd.zipPartitions(rdd2)((it1: Iterator[Int], it2: Iterator[Int]) => {
      it1.zip(it2)
    })





    rdd6.collect().foreach(println)
  }

}

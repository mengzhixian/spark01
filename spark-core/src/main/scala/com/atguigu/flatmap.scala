package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object flatmap {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(10,20,30,40)
    val list2 = List(20,30,10,40)
    val list3 = List(list1,list2)
    val rdd = sc.parallelize(list1)
    val rdd2 = rdd.flatMap(
      x => if(x >= 10) Array(x) else Array[Int]()


    )

//    val rdd2 = rdd.flatMap(x => if(x >= 50) Array(x) else Array[Int]())

//        val rdd2= rdd.flatMap(
//
//          s=>Array(s*s,s*s*s)
//
//    )

rdd2.collect().foreach(println)

    sc.stop()
  }

}

package com.atguigu.spark03

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object cache {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
         val list1 = List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8))
         val rdd1: RDD[(String, Int)] = sc.makeRDD(list1,2)
    val rdd2: RDD[(String, Int)] = rdd1.map {
      case (word, count) => (word, 1)
    }
    val rdd3: RDD[((String, Int), Int)] = rdd2.map {
      case (word, count) => ((word, count), 3)
    }
    rdd3
//    val rdd3: RDD[((String, Int), Int)] = rdd2.reduceByKey(_ + _).map {
//      case (word, count) => ((word, count), 1)
//    }

    rdd3.collect()
    rdd3.collect()
    Thread.sleep(10000000)
  }

}

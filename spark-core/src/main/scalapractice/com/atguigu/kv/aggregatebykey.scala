package com.atguigu.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object aggregatebykey {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
         val list1 = List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8))
         val rdd1: RDD[(String, Int)] = sc.makeRDD(list1,2)
//    val result: RDD[(String, (Int, Int))] = rdd1.aggregateByKey((Int.MaxValue, Int.MinValue))(
//      {
//        case ((max, min), v) => (max.min(v), min.max(v))
//
//      },
//      {
//        case ((max1, min1), (max2, min2)) => (max1 + max2, min1 + min2)
//      }
//
//    )
//    result.collect().foreach(println)

    sc.stop()
  }

}

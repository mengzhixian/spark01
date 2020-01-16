package com.atguigu

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object a {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8))
    val rdd1: RDD[(String, Int)] = sc.makeRDD(list1,2)
    val rdd2: RDD[(String, (Int, Int))] = rdd1.aggregateByKey((Int.MinValue, Int.MaxValue))(
      {
        case ((max, min), v) => (max.max(v), min.min(v))
      },
      {
        case ((max1, min1), (max2, min2)) => (max1 + max2, min1 + min2)
      }
    )
    rdd2.collect().foreach(println)
    sc.stop()
  }

}

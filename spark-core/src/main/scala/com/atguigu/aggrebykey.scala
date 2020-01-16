package com.atguigu

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object aggrebykey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8))
    val rdd1: RDD[(String, Int)] = sc.makeRDD(list1,2)
    val result: RDD[(String, (Int, Int))] = rdd1.aggregateByKey((0, 0))(
      {
        case ((sum, conut), v) => (sum + v, conut + 1)
      },
      {
        case ((sum1, count1), (sum2, coutnt2)) => ((sum1 + sum2), (count1 + coutnt2))

      }
    )
    result.collect().foreach(println)
    sc.stop()
  }

}

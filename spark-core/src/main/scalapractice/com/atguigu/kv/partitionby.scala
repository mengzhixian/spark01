package com.atguigu.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object partitionby {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
         val list1 = List(10,20,30,40,50,60,70)
         val rdd1: RDD[Int] = sc.parallelize(list1,2)
          val rdd2: RDD[(Int, Int)] = rdd1.map((_,1))
        val rdd3: RDD[(Int, Int)] = rdd2.partitionBy(new HashPartitioner(2))
    rdd3.collect().foreach(println)
    sc.stop()
  }
}

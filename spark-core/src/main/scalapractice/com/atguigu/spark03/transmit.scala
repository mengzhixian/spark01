package com.atguigu.spark03

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object transmit {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()

      .setAppName("SerDemo")
      .setMaster("local[*]")
    val sc = new SparkContext(conf)
    val rdd: RDD[String] = sc.parallelize(Array("hello world", "hello atguigu", "atguigu", "hahah"), 2)
    val searcher = new Searcher("hello")
    val result: RDD[String] = searcher.getMatchedRDD1(rdd)

  }

}
 class Searcher(val query: String){
   def isMatch(s: String): Boolean = {
     s.contains(query)

   }
   def getMatchedRDD1(rdd: RDD[String]): RDD[String] = {
     rdd.filter(isMatch)
   }

}
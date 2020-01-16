package com.atguigu.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object practice2 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("practice")
    val sc = new SparkContext(conf)
    val rdd1: RDD[String] = sc.textFile("D:\\agent.log")
    val rdd2= rdd1.map(
      s => {
        val words: Array[String] = s.split(" ")

        ((words(1), words(4)),1)
      }
    )
    rdd2.reduceByKey(_+_).map{
      case ((pro,ads),count)=>((pro),(ads,count))
    }.groupByKey().map{
      case(key,it)=>(key,it.toList.sortBy(_._2)(Ordering.Int.reverse).take(3))
    }.sortBy(_._1.toInt)

  }

}

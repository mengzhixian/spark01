package com.atguigu.kv

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object practice {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("practice")
    val sc = new SparkContext(conf)
    val rdd1: RDD[String] = sc.textFile("D:\\agent.log")
    val rdd2: RDD[((String, String), Int)] = rdd1.map(
      t => {
        val words: Array[String] = t.split(" ")
        ((words(1), words(4)), 1)
      }
    )
    rdd2.reduceByKey(_+_).map{
      case ((pro,ads),count)=>(pro,(ads,count))
    }.groupByKey().map{
      case (key,it)=>(key,it.toList.sortWith(_._2>_._2).take(3))
    }.sortByKey().collect().foreach(println)
    sc.stop()

  }

}

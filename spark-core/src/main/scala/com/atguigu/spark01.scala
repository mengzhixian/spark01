package com.atguigu
import org.apache.spark.{SparkConf, SparkContext}
object spark01 {
  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkConf对象, 并设置 App名字, 并设置为 local 模式
    val conf: SparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    // 2. 创建SparkContext对象
    val sc = new SparkContext(conf)
    // 3. 使用sc创建RDD并执行相应的transformation和action
    val wordAndCount: Array[(String, Int)] = sc.textFile("D:\\sparknode")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
      .collect()
    wordAndCount.foreach(println)
    // 4. 关闭连接
    sc.stop()

  }

}

package com.atguigu

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object distinct01 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
    val sc = new SparkContext(conf)
    val list1 = List(User("ss",3),User("sd",3))
    val rdd1: RDD[User] = sc.makeRDD(list1).distinct()

    rdd1.collect().foreach(println)
    sc.stop()
  }

}

case class User(name:String,age:Int){
  override def equals(obj: Any): Boolean = this.age==age

  override def hashCode(): Int = age
}

package com.atguigu.spark.project.app

import com.atguigu.spark.project.bean.UserVisitAction
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object ProjectApp {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
       val sourcerdd: RDD[String] = sc.textFile("D:\\user_visit_action.txt")
    val userVisitActionRDD: RDD[UserVisitAction] = sourcerdd.map(line => {
      val splits: Array[String] = line.split("_")
      UserVisitAction(
        splits(0),
        splits(1).toLong,
        splits(2),
        splits(3).toLong,
        splits(4),
        splits(5),
        splits(6).toLong,
        splits(7).toLong,
        splits(8),
        splits(9),
        splits(10),
        splits(11),
        splits(12).toLong)

    }
    )
    val categoryTop10 = CategoryTopApp.statCategoryTop10(sc, userVisitActionRDD)
    CategoryTop10SessionApp.CategorySessionTop10_3(sc,categoryTop10,userVisitActionRDD)
sc.stop()
  }

}

package com.atguigu.project.app

import com.atguigu.project.bean.UserVisitAction
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object ProjectApp {
  def main(args: Array[String]): Unit = {
    // 1. 读取数据
    val conf: SparkConf = new SparkConf().setAppName("ProjectApp").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    val sourceRDD: RDD[String] = sc.textFile("D:\\user_visit_action.txt")
    // 1.1 封装数据
    val userVisitActionRDD: RDD[UserVisitAction] = sourceRDD.map(line => {
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

    })
    // 1.2 数据清洗


    // 2. 需求1:
    CategoryTopApp.statCategoryTop10(sc, userVisitActionRDD)

    // 3. 需求2:

    // 4. 需求3:


    sc.stop()
  }

}

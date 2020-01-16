package com.atguigu.spark.project.app

import com.atguigu.spark.project.acc.CategoryAcc
import com.atguigu.spark.project.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object CategoryTopApp {
  def  statCategoryTop10(sc: SparkContext, userVisitActionRDD: RDD[UserVisitAction])={
      val acc = new CategoryAcc
    sc.register(acc,"wr")
    userVisitActionRDD.foreach(action=>
    acc.add(action)
    )
    val cat= acc.value.groupBy(_._1._1).map {
      case (cid, map) =>
        CategoryCountInfo(cid,
          map.getOrElse((cid, "click"), 0),
          map.getOrElse((cid, "order"), 0),
          map.getOrElse((cid, "pay"), 0))
    }.toArray
    cat.sortBy(info=>(-info.clickCount,-info.orderCount,-info.payCount)).take(10)
  }
}

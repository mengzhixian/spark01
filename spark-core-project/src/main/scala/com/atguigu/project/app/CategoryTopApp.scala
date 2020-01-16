package com.atguigu.project.app

import com.atguigu.project.acc.CategoryAcc
import com.atguigu.project.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object CategoryTopApp {
  def statCategoryTop10(sc: SparkContext, userVisitActionRDD: RDD[UserVisitAction]) = {
    val acc = new CategoryAcc
    sc.register(acc, "CategoryAcc")
    // 变量RDD, 计算每个cid的3个指标
    userVisitActionRDD.foreach(action => {
      acc.add(action)
    })
    // Map[("cid, click") -> 1000]
    val cidActionAndCountGrouped: Map[String, Map[(String, String), Long]] = acc.value.groupBy(_._1._1)
    val categroyCountInfos: Array[CategoryCountInfo] = cidActionAndCountGrouped.map {
      case (cid, map) =>
        CategoryCountInfo(cid,
          map.getOrElse((cid, "click"), 0),
          map.getOrElse((cid, "order"), 0),
          map.getOrElse((cid, "pay"), 0))
    }.toArray

    //        categroyCountInfos.sortBy(info => (info.clickCount, info.orderCount, info.payCount))(Ordering.Tuple3(Ordering.Long.reverse, Ordering.Long.reverse, Ordering.Long.reverse))
    categroyCountInfos
      .sortBy(info => (-info.clickCount, -info.orderCount, -info.payCount))
      .take(10)
      .foreach(println)
  }
}

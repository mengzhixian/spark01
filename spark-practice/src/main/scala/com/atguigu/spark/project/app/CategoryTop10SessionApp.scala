package com.atguigu.spark.project.app

import com.atguigu.spark.project.bean.{CategoryCountInfo, SessionInfo, UserVisitAction}
import org.apache.spark.{Partitioner, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

object CategoryTop10SessionApp {
  def CategorySessionTop10(sc: SparkContext, categoryTop10: Array[CategoryCountInfo], userVisitActionRDD: RDD[UserVisitAction]) = {
    val cids: Array[Long] = categoryTop10.map(_.categoryId.toLong)
    val value: RDD[UserVisitAction] = userVisitActionRDD.filter(action => cids.contains(action.click_category_id))
    val resu: RDD[(Long, List[(String, Int)])] = value.map(action => ((action.click_category_id, action.session_id), 1)).reduceByKey(_ + _).map {
      case ((cid, sid), count) => (cid, (sid, count))
    }.groupByKey().mapValues(it => it.toList.sortBy(-_._2).take(10))
    resu.foreach(println)
  }
  def CategorySessionTop10_3(sc: SparkContext, categoryTop10: Array[CategoryCountInfo], userVisitActionRDD: RDD[UserVisitAction])={
    val cids = categoryTop10.map(_.categoryId.toLong)
    val fil: RDD[UserVisitAction] = userVisitActionRDD.filter(t =>
      cids.contains(t.click_category_id)
    )
    val value: RDD[((Long, String), Int)] = fil.map(action=>((action.click_category_id,action.session_id),1))
    val value2: RDD[(Long, (String, Int))] = value.reduceByKey(new CategoryPartitioner(cids), _ + _).map {
      case ((cid, sid), count) => (cid, (sid, count))

    }
    val resultRDD: RDD[(Long, SessionInfo)] = value2.mapPartitions(it => {
      var set = new mutable.TreeSet[SessionInfo]()
      var catid = 0L
      it.foreach {
        case (cid, (sid, count)) => catid=cid
          set += SessionInfo(sid, count)
          if (set.size > 10) set = set.take(10)
      }
      set.map((catid, _)).toIterator
    })

    resultRDD.collect.foreach(println)
  }
}
class CategoryPartitioner(cids: Array[Long]) extends Partitioner {
  // 使用cid的索引作为他将来的分区所用
  private val map: Map[Long, Int] = cids.zipWithIndex.toMap

  // 分区的个数设置为和品类的id树保持一致, 将来保证一个分区内只有一个品类的数据
  override def numPartitions: Int = cids.length

  // 根据key来返回这个key应去的那个分区的索引
  override def getPartition(key: Any): Int = {
    key match {
      case (k: Long, _) => map(k)
    }
  }
}

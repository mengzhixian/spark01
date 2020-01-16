package com.atguigu.spark.project.acc

import com.atguigu.spark.project.bean.UserVisitAction
import org.apache.spark.util.AccumulatorV2

class CategoryAcc extends AccumulatorV2[UserVisitAction,Map[(String, String), Long]]{
  private var map:Map[(String, String), Long]=Map[(String, String), Long]()
  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[UserVisitAction, Map[(String, String), Long]] = {
    val acc = new CategoryAcc
    acc.map++=map
    acc
  }

  override def reset(): Unit = map=Map[(String, String), Long]()

  override def add(v: UserVisitAction): Unit = {
    if (v.click_category_id != -1){
      map+=(v.click_category_id.toString,"click")->(map.getOrElse((v.click_category_id.toString,"click"),0L)+1L)
    }
    else if (v.order_category_ids != "null"){
     val strings: Array[String] = v.order_category_ids.split(",")
      strings.foreach(cid=>
        map += (cid, "order") -> (map.getOrElse((cid, "order"), 0L) + 1L)
      )

    }else if(v.pay_category_ids!= -1) {
      val strings: Array[String] = v.pay_category_ids.split(",")
      strings.foreach(cid=>
      map+=(cid,"pay")->(map.getOrElse((cid,"pay"),0L)+1L)
      )
    }
  }

  override def merge(other: AccumulatorV2[UserVisitAction, Map[(String, String), Long]]): Unit = other match {
    case o:CategoryAcc=>{
      o.map.foreach{
        case (cid,count)=>
          this.map+=cid->(this.map.getOrElse(cid,0L)+count)
        case _=>throw  new Exception("dgg")
      }
    }
  }

  override def value: Map[(String, String), Long] = map
}

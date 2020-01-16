package com.atguigu.spark03.com.atguigu.spark04

import org.apache.spark.util.AccumulatorV2

class myadd extends AccumulatorV2[Long, Long] {
  private var _sum = 0L

  override def isZero: Boolean = _sum == 0L

  override def copy(): AccumulatorV2[Long, Long] = {
    val acc = new myadd
    acc._sum = this._sum
    acc
  }

  override def reset(): Unit = _sum = 0L

  override def add(v: Long): Unit = _sum += v

  override def merge(other: AccumulatorV2[Long, Long]): Unit = other match {
    case o: myadd => this._sum += o._sum
    case _ => throw new Exception("..")
  }


  override def value: Long = _sum
}

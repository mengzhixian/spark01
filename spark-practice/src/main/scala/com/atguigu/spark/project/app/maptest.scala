package com.atguigu.spark.project.app

object maptest {
  def main(args: Array[String]): Unit = {
    val map: Map[String, Int] = Map("a"->1,"b"->2,"c"->3)
    println(map("a"))
  }

}

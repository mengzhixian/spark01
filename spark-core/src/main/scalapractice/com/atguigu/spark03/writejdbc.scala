package com.atguigu.spark03

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.{SparkConf, SparkContext}

object writejdbc {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
         val list1 = List((2,"w2"),(3,"w3"),(4,"w4"))
         val rdd1 = sc.makeRDD(list1,2)
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://192.168.6.103:3306/mydb2"
    val userName = "root"
    val passWd = "332892793"
    val sql = "insert into User values(?, ?)"


    rdd1.foreachPartition(it => {
      Class.forName(driver)
      val conn: Connection = DriverManager.getConnection(url, userName, passWd)

      val ps: PreparedStatement = conn.prepareStatement(sql)
      it.foreach {
        case (age, name) =>
          ps.setInt(1, age)
          ps.setString(2, name)
          ps.addBatch()
      }
      ps.executeBatch()
      conn.close()
    })
  }

}

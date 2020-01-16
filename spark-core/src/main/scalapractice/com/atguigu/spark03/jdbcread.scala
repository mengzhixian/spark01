package com.atguigu.spark03

import org.apache.spark.{SparkConf, SparkContext}

object jdbcread {
  def main(args: Array[String]): Unit = {
     val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("flatmap")
         val sc = new SparkContext(conf)
    sc.textFile("D:\\sparknode")
//             val driver= "com.mysql.jdbc.Driver"
//                 val url = "jdbc:mysql://192.168.6.103:3306/mydb2"
//                 val userName = "root"
//                 val passWd = "332892793"
//                 val rdd: RDD[(Int, String)] =  new JdbcRDD(
//                   sc,
//                   () => {
//                     Class.forName(driver)
//                     DriverManager.getConnection(url, userName, passWd)
//                   },
//                   "select * from User where age >= ? and age <= ?",
//                   0,
//                   50,
//                   2,
//                   (resultSet: ResultSet) => {
//                     (resultSet.getInt(1), resultSet.getString(2))
//                   }
//                 )
//
//                 rdd.collect().foreach(println)


  }

}

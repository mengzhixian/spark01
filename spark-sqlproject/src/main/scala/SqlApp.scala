import org.apache.spark.sql.SparkSession

object SqlApp {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("SqlApp")
      .enableHiveSupport()
      .getOrCreate()
    spark.sql("use sql0830")
    spark.sql(
      """
        |select ci.*
        |pi.product_name
        |uv.click_product_id
        |from user_visit_action uv
        |join product_info pi on uv.product_id=pi.procuct_id
        |join city_info ci on uv.city_id=ci.city_id
      """.stripMargin
    ).createOrReplaceTempView("t1")


    spark.udf.register("remark",RemarkUDAF)
  }
}

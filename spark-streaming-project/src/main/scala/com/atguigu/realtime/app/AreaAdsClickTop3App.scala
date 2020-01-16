package com.atguigu.realtime.app
import com.atguigu.realtime.bean.AdsInfo
import com.atguigu.realtime.util.RedisUtil
import org.apache.spark.streaming.dstream.DStream
import org.json4s.jackson.JsonMethods
import redis.clients.jedis.Jedis

object AreaAdsClickTop3App extends APP {

  override def doSomething(ads: DStream[AdsInfo]): Unit = {
    val preKey="day:area:ads"
    ads.print()
    //每天每地区点击量
    val dayAreaAdsAndCountStreamit: DStream[((String, String, String), Int)] = ads.map(info => ((info.dayString, info.area, info.adsId), 1))
      .updateStateByKey((seq: Seq[Int], opt: Option[Int]) => {
        Some(seq.sum + opt.getOrElse(0))
      })

    //地区分组
    val dayAreaGroupedStream: DStream[((String, String), Iterable[(String, Int)])] = dayAreaAdsAndCountStreamit.map {
      case ((day, area, ads), count) => ((day, area), (ads, count))
    }.groupByKey
    //排序取前三(是迭代器里面的内容)
    val resultStream: DStream[((String, String), List[(String, Int)])] = dayAreaGroupedStream.map {
      case ((day, area), adsCountIt) =>
        ((day, area), adsCountIt.toList.sortBy(-_._2).take(3))
    }
    //数据存到redis
    resultStream.foreachRDD(rdd => {
      rdd.foreachPartition((it: Iterator[((String, String), List[(String, Int)])]) => {
        // 建立到redis的连接
        val client: Jedis = RedisUtil.getJedisClient
        // 遍历it, 把数据写入到redis
        it.foreach{
          case ((day, area), adsCountList) =>
            // redis中的key
            val key: String = preKey + day
            // hash: field
            val field: String = area
            // hash: value adsCountList变成json字符串   json4s
            import org.json4s.JsonDSL._
            val value: String = JsonMethods.compact(JsonMethods.render(adsCountList))

            client.hset(key, field, value)
        }

        // 关闭到redis的连接
        client.close()
      })
    })
  }
}

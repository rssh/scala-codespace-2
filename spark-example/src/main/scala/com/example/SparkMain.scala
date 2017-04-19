package com.example

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Main {

  def main(args: Array[String]):Unit =
  {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[2]")
    val sc = new StreamingContext(conf, Seconds(10) )

    try {
      //val text: RDD[String] = sc.textFile(args(0))
      val text = sc.socketTextStream("127.0.0.1",9999)

      val counts:DStream[(String,Int)] = text.flatMap(line => line.split(" "))
        .map(word => (word, 1))
        .reduceByKey(_ + _)

      val top100 = counts.map{ case (x,y) => Map(x->y) }.reduce{
        (a,b) => (a ++ b)
      }.map(_.toSeq.sortBy(_._2).take(100))

      top100.print(100)

      sc.start()
      sc.awaitTermination()

    }finally{

      sc.stop()
    }

  }


}

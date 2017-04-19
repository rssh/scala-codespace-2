package com.example.upload.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.language.postfixOps





case class Point(x:Int,y:Int)

object Main
{


  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  // TODO:  get from config, setup parallelism
  implicit val executionContext = actorSystem.dispatcher


  def main(args: Array[String]):Unit =
  {

    val (host, port) = ("localhost", 8080)
    val serverSource = Http().bind(host, port)

    val echo = RequestFlow()

    serverSource
      .runForeach { con =>
        con.handleWith(echo)
      }


  }



}

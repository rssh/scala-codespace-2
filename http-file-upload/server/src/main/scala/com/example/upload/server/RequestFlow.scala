package com.example.upload.server

import java.nio.charset.StandardCharsets
import java.util.concurrent.atomic.AtomicLong

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.HttpCookie
import akka.stream.scaladsl.Flow
import io.circe.generic.auto._
import io.circe.parser._

import scala.concurrent.Future
import scala.concurrent.duration._

object RequestFlow {

  import Main._

  def apply() = {
    Flow[HttpRequest].mapAsync(10)(handler _)
  }

  val cookieName = "FTRANSFER"

  val cookieCounts = new AtomicLong(10L)

  def handler(request:HttpRequest):Future[HttpResponse] =
  {
    System.err.println(s"headers: ${request.headers}")
    for{
         strictEntity <- request.entity.toStrict(1 second)
          s = strictEntity.getData().decodeString(StandardCharsets.UTF_8)
         _ = Console.println(s"received $s")
         packet <- eitherAsFuture(decode[FileTransferPacket](s))
    } yield {

      val cookie = retrieveCookie(request)

      val aRef = actorSystem.actorOf(FileConsumerActor.props(cookie))
      aRef ! packet

      HttpResponse(
        entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "OK")
      ).withHeaders(headers.`Set-Cookie`(HttpCookie(cookieName,cookie)))

    }

  }


    def eitherAsFuture[A,B<:Throwable](x:Either[Throwable,A]):Future[A] =
      x match {
        case Left(e) => Future failed e
        case Right(x) => Future successful x
      }


    def retrieveCookie(request:HttpRequest):String =
    {
      val cookies = request.header[headers.Cookie]
      val cookie = cookies match {
        case None => cookieCounts.incrementAndGet().toString
        case Some(c) => c.cookies.filter(_.name==cookieName).headOption match {
          case None => cookieCounts.incrementAndGet().toString
          case Some(v) => v.value
        }
      }
      cookie
    }

  }

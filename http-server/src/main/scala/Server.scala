import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives
import akka.stream.scaladsl.Flow
import common.Item

import scala.io.StdIn
import scala.concurrent.Future
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream._
import akka.stream.scaladsl._
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.NoTypeHints
import org.json4s.native.Serialization


object Demo extends App with Directives with Json4sSupport {
  implicit val system = ActorSystem("my-system")
  implicit val execCtx = system.dispatcher
  implicit val materializer = ActorMaterializer()

  implicit val serialization = Serialization
  implicit val defaultFormats = Serialization.formats(NoTypeHints)

  val route =
    path("hello") {
      get {
        complete(HttpResponse(200, entity = HttpEntity(
          ContentTypes.`text/html(UTF-8)`,
          "<h1>Say hello to akka-http</h1>")))
      }
    } ~
    path("randomitem") {
      get {
        // will marshal Item to JSON
        complete(Item("thing", 42))
      }
    } ~
      path("saveitem") {
        println("inside saveitem")
        post {
          // will unmarshal JSON to Item
          println("inside post")
          entity(as[Item]) { item =>
            println(s"Server saw Item : $item")
            complete(item)
          }
        }
      }

  val (host, port) = ("localhost", 8080)
  val bindingFuture = Http().bindAndHandle(route, host, port)

  bindingFuture.onFailure {
    case ex: Exception =>
      println(s"$ex Failed to bind to $host:$port!")
  }

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
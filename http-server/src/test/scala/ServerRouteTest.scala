import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{CustomSerializer, DefaultFormats, Extraction, NoTypeHints}
import org.json4s.JsonAST._
import org.json4s.JsonDSL._
import org.json4s.ext.EnumSerializer
import org.json4s.native.Serialization
import org.json4s.native.JsonMethods._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Success
import scala.language.postfixOps

class RegisterParticipantTest extends WordSpec with Matchers with ScalatestRouteTest with Json4sSupport {
  //implicit val actorSystem = ActorSystem()

  implicit val formats = DefaultFormats
  implicit val serialization = Serialization

  val route = Demo.route

  "The service" should {
    "handle POST request" in {
      val itemJs: JValue = ("name" -> "Hi") ~ ("id" -> 15)
      Post("/saveitem", itemJs) ~> route ~> check {
        response.status shouldEqual StatusCodes.OK
        val jsResponse = responseAs[JValue]
        jsResponse \ "name" shouldEqual JString("Hi")
        jsResponse \ "id" shouldEqual JString("15")
      }
    }
  }

}
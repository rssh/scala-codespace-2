package example

import org.scalatest._

class MapProcessorSpec extends FlatSpec with Matchers {
  val testMap = Map(
    "A" -> Seq(Some(2),None,Some(3)),
    "B" -> Seq(None,Some(1),Some(2)),
    "B" -> Seq(None,Some(1),Some(4))
  )

  val processedTestMap = Map(
    "A" -> Seq(Some(2),Some(1),Some(3)),
    "B" -> Seq(Some(2),Some(1),Some(2)),
    "B" -> Seq(Some(2),Some(1),Some(4))
  )

  implicit val averageableSeqInt = new AverageableSeq[Int] {
    def average(s: Seq[Int]) = s.sum / s.length
  }

  "MapProcessor" should "process testMap" in {
    MapProcessor.processMap(testMap) shouldEqual processedTestMap
  }

}

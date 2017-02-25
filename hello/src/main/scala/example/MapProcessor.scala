package example

object MapProcessor {
  def processMap[K,V: AverageableSeq](map: Map[K, Seq[Option[V]]]) = {
    val n = map.values.head.length
    // make a sequence of sequences of all defined values
    val valueSeqs = map.values.foldLeft(Seq.fill(n)(Seq.empty[V])) { (acc, a) =>
      val zipped = acc zip a
      zipped map {
        case (seq, Some(a)) => a +: seq
        case (seq, None) => seq
      }
    }
    val averageable = implicitly[AverageableSeq[V]]
    val averages = valueSeqs map averageable.average
    map.mapValues{seq =>
      val zipped = seq zip averages
      zipped map {
        case (None, average) => Some(average)
        case (x, _) => x
      }
    }
  }

}

trait AverageableSeq[T] {
  def average(ts: Seq[T]): T
}
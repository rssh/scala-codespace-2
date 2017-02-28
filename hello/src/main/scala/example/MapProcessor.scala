package example

object MapProcessor {

  //
  //
  //
  def fillColumnGaps[V](v:Vector[Option[V]], aggregate:Vector[V]=>V):Vector[V] =
  {
    //val nonEmpty: Vector[V] = v.filter(_.isDefined).map(_.get)
    //val filler = aggregate(nonEmpty)
    //v map (_.getOrElse(filler))
    val nonEmpty = for(x <- v if x.isDefined ) yield x.get
    val filler = aggregate(nonEmpty)
    for(x <- v) yield x.getOrElse(filler)
  }

  
  def fillMatrixGaps[V](v:Vector[Vector[Option[V]]],aggregate: Vector[V]=>V) : Vector[Vector[V]] = {
    v map (x => fillColumnGaps(x,aggregate))
  }


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
package com.example.life

class LifeImpl extends Life {

  case class FieldImpl(v: Set[Point], val xMax:Int, val yMax:Int) extends Field
  {



    /**
      * Assume that x & y can be bigger that actual fiedl
      */
    override def isAlive(p: Point): Boolean =
      v.contains(p)


    /**
      * iterate all
      *
      * @param f : function to apply
      * @return
      */
    override def map(f: (Point) => Boolean): Field =
    {
      val toSee = v.flatMap(this.neighbors) union v
      val out = toSee.filter(f)
      new FieldImpl(out,xMax,yMax)
    }

     def neighbors(p:Point):Set[Point] =
     {
      neighbourOffsets.map(d => applyOffset(p, d._1,d._2) )
     }

     override def foreach(f: (Point,Boolean) => Unit): Unit =
     {
       val all = (0 until xMax).flatMap(
         x => (0 until yMax) map {
           y => val p = Point(x, y)
                (p,isAlive(p))
         }
       )
       all.foreach(f.tupled)
     }


  }

  override def initField(maxX: Int, maxY: Int,
                         liveCells: Set[Point]): Field =
  {
    assume(liveCells.forall(
               p=> p.x < maxX && p.x >= 0 &&
                   p.y < maxY && p.y >= 0
    ),"exists fields")
    FieldImpl(liveCells,maxX,maxY)
  }


}

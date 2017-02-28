package com.example.life

class LifeImpl extends Life {

  case class FieldImpl(v: Set[Point], val xMax:Int, val yMax:Int) extends Field
  {



    /**
      * Assume that x & y can be bigger that actual field
      */
    override def isAlive(p: Point): Boolean =
      v.contains(p)


    /**
      * iterate all
      *
      * @param f : function to apply
      * @return
      */
    override def filter(f: (Point) => Boolean): Field =
    {
      val toSee = v.flatMap(this.neighbors) union v
      val out = toSee.filter(f)
      new FieldImpl(out,xMax,yMax)
    }

    def neighbors(p:Point):Set[Point] = {
     // neighbourOffsets.map(d => applyOffset(p, d._1, d._2))
     // neighbourOffsets.map{ case (dx,dy) => applyOffset(p, dx, dy))
     for((dx,dy) <- neighbourOffsets) yield applyOffset(p,dx,dy)
    }


    override def foreach(f: Point => Unit): Unit = {

      /*
      val all = (0 until xMax).flatMap(
        x => (0 until yMax) map {
          y =>
            val p = Point(x, y)
            (p, isAlive(p))
        }
      )
      */
      for {x <- 0 until xMax
           y <- 0 until yMax
      } {
        f(Point(x,y))
      }


    }

    def stringState(): String = {
      println(s"printState: v=${v}")
      def printCell(p: Point, isAlive: Boolean, sb:StringBuilder): Unit = {
        sb.append(if (isAlive) "X" else " ")
        if (p.y == yMax - 1) sb.append("\n")
      }

      val sb = new StringBuilder

      //this.foreach((p,f) => printCell(p,f,sb))
      for(p <- this) {
        printCell(p,isAlive(p),sb)
      }


      sb.toString()
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

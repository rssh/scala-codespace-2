package com.example.life

case class Point(x:Int,y:Int)


trait Life {

  trait Field {

    /**
      * Assume that x & y can be bigger that actual fiedl
      */
    def isAlive(p:Point):Boolean

    /**
      * iterate all
      * @param f: function to apply
      * @return
      */
    def map(f:Point=>Boolean):Field

    def foreach(f:(Point,Boolean)=>Unit):Unit

    def xMax:Int

    def yMax:Int

    def applyOffset(p:Point, dx:Int,dy:Int)=
       Point((p.x + dx + xMax) % xMax, (p.y + dy + yMax)%yMax )

    def nextAction(p:Point): Boolean =
    {
      val n = neighbourOffsets.map{ case (dx,dy) =>
          applyOffset(p,dx,dy)
        }.count(isAlive)

      (isAlive(p), n) match {
        case (false,3) => true
        case (true,x) if (x==2 || x==3) => true
        case _ => false
      }
    }

    def printState(): Unit

  }

  def step(field:Field):Field = field.map(field.nextAction)

  def initField(maxX:Int, maxY:Int, liveCells:Set[Point]):Field


  val neighbourOffsets = Set(
    (-1,-1),(-1,0),(-1,1),
    (0 ,-1),       (0 ,1),
    (1, -1),(1 ,0),(1 ,1)
  )

}

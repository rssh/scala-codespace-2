package com.example.life

case class Point(x:Int,y:Int)
{
  def applyOffset(dx:Int,dy:Int)=Point(x+dx,y+dy)
}

trait Life {



  trait Field {

    /**
      * Assume that x & y can be bigger that actual fiedl
      */
    def isAlive(p:Point):Boolean

    def nextAction(p:Point): Boolean =
    {
      val n = neighbourOffsets.map{ case (dx,dy) =>
          p.applyOffset(dx,dy)
        }.filter(isAlive(_)).size

      

      ???
    }

  }

  def step(field:Field):Field = ???


  var current: Field


  val neighbourOffsets = Set(
    (-1,-1),(-1,0),(-1,1),
    (0 ,-1),       (0 ,1),
    (1, -1),(1 ,0),(1 ,1)
  )

}

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

    /**
      * iterate all
      * @param f
      * @return
      */
    def map(f:Point=>Boolean):Field

    def nextAction(p:Point): Boolean =
    {
      val n = neighbourOffsets.map{ case (dx,dy) =>
          p.applyOffset(dx,dy)
        }.count(isAlive)

      (isAlive(p), n) match {
        case (false,3) => true
        case (true,x) if (x==2 || x==3) => true
        case _ => false
      }
    }


  }

  def step(field:Field):Field = field.map(field.nextAction)


  var current: Field


  val neighbourOffsets = Set(
    (-1,-1),(-1,0),(-1,1),
    (0 ,-1),       (0 ,1),
    (1, -1),(1 ,0),(1 ,1)
  )

}

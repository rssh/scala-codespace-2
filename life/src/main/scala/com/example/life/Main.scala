package com.example.life

object Main {

  def main(args:Array[String]):Unit =
  {
    val life = createLife
    var state  = life.initField(10,10,
        Set( (1,0),(2,0),(3,0) ) map (x => Point(x._1,x._2))
    )

    var i = 0
    while(i < 5) {
      state = life.step(state)
      state.printState()
      Console.in.read()
      i += 1
    }

  }

  //   f :A,B => C
  //   f.tupled: (A,B) => C

  //   f: A,B,C => D
  //   f.tupled: (A,B,C) => D

  def createLife = new LifeImpl()


}

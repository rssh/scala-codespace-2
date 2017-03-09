package com.codespace.bargaining

import scala.util.Try

class GameRunner extends GameAPI {


  override def play(x: Agent, y: Agent, sum: Double): Try[GameResult] =
  Try{
    val p = x.startGame(sum,y)
    val r = if (y.finishGame(sum,x,p))
      p.split(sum)
    else
      Disagree
    System.err.println(s"game $x $y, proposion $p result $r")
    r
  }

}

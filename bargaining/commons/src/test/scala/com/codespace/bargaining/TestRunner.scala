package com.codespace.bargaining

import scala.util.Try

class TestRunner extends GameAPI {


  override def play(x: Agent, y: Agent, sum: Double): Try[GameResult] =
  Try{
    val p = x.startGame(sum,y)
    if (y.finishGame(sum,x,p))
      p.split(sum)
    else
      Disagree
  }

}

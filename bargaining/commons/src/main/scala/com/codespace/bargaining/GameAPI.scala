package com.codespace.bargaining

import scala.util.Try

sealed trait GameResult
case class Agree(x:Double,y:Double) extends GameResult
case object Disagree extends GameResult

case class Proposition(val v: Double)
{

  def split(sum:Double):Agree =
    Agree(sum*v, sum*(1.0-v))

}

trait GameAPI {


  def play(x:Agent,y:Agent,sum:Double):Try[GameResult]


}


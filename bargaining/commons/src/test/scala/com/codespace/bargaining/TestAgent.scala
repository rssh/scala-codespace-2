package com.codespace.bargaining

class TestAgent extends Agent {

  val diffThreshold = 0.2
  val defaultProposition = Proposition(0.5)

  override def startGame(sum: Double, party: Agent): Proposition =
  {
    defaultProposition
  }

  override def finishGame(sum: Double, party: Agent, proposition: Proposition): Boolean = {
    proposition.v - defaultProposition.v >= diffThreshold
  }

}

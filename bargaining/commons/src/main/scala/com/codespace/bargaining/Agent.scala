package com.codespace.bargaining


trait Agent {


  def startGame(sum:Double, party:Agent):Proposition

  def finishGame(sum:Double, party:Agent, proposition:Proposition):Boolean

}

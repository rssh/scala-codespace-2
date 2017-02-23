package com.example.life


trait Life {


  trait Field {

    def isAlive(x:Int,y:Int):Boolean

    def nextAction(x:Int,y:Int): Boolean =
    {
      ???
    }

  }

  def step(field:Field):Field = ???


  var current: Field

}

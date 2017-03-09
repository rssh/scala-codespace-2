package com.codespace.bargaining

import org.scalatest.FunSuite

class SimpleGameTest extends FunSuite
{

  test("simple game")  {
    val runner = new TestRunner
    val a = new TestAgent
    val b = new TestAgent
    val result = runner.play(a,b,10.0)
    System.err.println("result:"+result)
    assert(result.isSuccess)
    assert(result.get.isInstanceOf[Agree])

    val Agree(ar,br) = result.get
    assert(Math.abs(ar - 5.0) < 1e-3)
    assert(Math.abs(br - 5.0) < 1e-3)

  }

}

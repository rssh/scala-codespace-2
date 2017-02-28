package example

import org.scalatest.{FunSpec, FunSuite}

class MyListSpec extends FunSuite {

  test("check simple reverse ") {
   //MyList(1,2,3)
    val l = MyList.apply(1,2,3)

    assert(l.reverse() == MyList(3,2,1))
  }

  test("reverse of Nil is Nil") {
    assert(MyList().reverse === MyNil)
  }

  test("check reverse of wrapped array") {
    //MyList(1,2,3)
    val l = MyList.apply(Array(1,2,3).toSeq: _*)

    assert(l.reverse() == MyList(3,2,1))
  }



}

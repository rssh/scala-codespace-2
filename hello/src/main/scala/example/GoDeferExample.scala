package example

class GoDeferExample {


  Scoped{ scope =>
    val f1 = new FileInputStream("f1")
    scope.deferr{ f1.close() }
    val f1 = new FileInputStream("f2")
    scope.deferr{ f1.close() }
  }


}

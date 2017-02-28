import example.{MyCons, MyList, MyNil}

def length[X](l:MyList[X]) =
  l match {
    case MyCons(head,tail) => 1 + length(tail)
    case MyNil =>  0
  }



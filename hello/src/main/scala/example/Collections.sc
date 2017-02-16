import example.MyNil

val l1:List[Int] = 1 :: 2 :: 3 :: Nil

val l2 = List(1,2,3)

val ml = 1::2::3::4::MyNil

ml.foldRight("*")( (x,y) => "(" + x + "," + y + ")")

ml map (_ +1)
package example

/**
  * Created by rssh on 2/16/17.
  */
object LunStas {

  val n = "4561261212345467"
  n.toCharArray.map(_.asDigit).zipWithIndex.map{
    case (element, index) =>
       val multiplier = 2-(index % 2)
       val multiplicated = element * multiplier
       if (multiplicated > 9) multiplicated - 9 else multiplicated
    }.sum % 10 == 0

}

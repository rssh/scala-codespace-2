package example

object TaggedScope {

  trait Tagged[B] {
    type Tag = B
  }

  type @@[A,B] = A with Tagged[B]

  def @@[A,B](x:A): @@[A,B] = x.asInstanceOf[A@@B]

}

import TaggedScope._

case class Customer(id: Long@@Customer, name: String)

case class Subscriber(id: Long@@Subscriber, name: String)


object DAO {

  def createOrRetrieveCustomer(id: Long @@ Customer): Customer = ???

}


package com.example1

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

case class Task[A](fex:Future[Either[String,A]])
{


  def map[B](f:A=>B):Task[B] =
    Task{ fex.map{ ex => ex.map(f) }}

  def flatMap[B](f: A=>Task[B]):Task[B] = {
    val xf: Future[Either[String,Task[B]]] = fex map { ex => ex.map(f) }
    // TODO:
    //   // tailRecM
    ???
    //xf.flatMap(xfe => xfe.flatMap(t=>t.inner))

    /*
    for{ex <- fex
         taskB = ex map f
         feb = taskB.inner
         // TODO
         */

  }

  //def flatten

  def inner = fex


  def unsafeRun():A = {
    val ex = Await.result(fex,1 minute)
    ex match {
      case Right(v) => v
      case Left(x) => throw new IllegalStateException(x)
    }
  }

}


class CurrencyBoard {

}

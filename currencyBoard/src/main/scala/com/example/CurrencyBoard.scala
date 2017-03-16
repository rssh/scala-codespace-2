package com.example

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


case class Money(amount: BigDecimal,
                 currency: Symbol)

case class CurrencyRate(ask:BigDecimal,
                        bid:BigDecimal,
                        amount:BigDecimal,
                        symbol:Symbol,
                        baseSymbol: Symbol)



trait CurrencyBoard {


  // def ,... A => M[B],    Kleisly(M,A,B)

  def rates():Future[Seq[CurrencyRate]]

  def baseSymbols():Future[CurrencyRate]

  def exchange(from:Symbol,to:Symbol,fromAmount:BigDecimal):Future[Either[String,BigDecimal]]

}

object X
{

  def printCurrentData(cb:CurrencyBoard): Unit =
  {
    val f:Future[String] = for{r <- cb.rates()
                symbols <- cb.baseSymbols()
               } yield {
       s"""symbols: $symbols
           rates: $r
        """
    }
    //cb.rates().flatMap{ r => cb.baseSymbols.map{ s => s":$s" }}

    val message = Await.result(f, 1 minute)
    Console.println(message)
  }

  val xPortfilio = List(1.0 ->'USD,1.0->'EUR,300.0 ->'VON, 0.4->'BTC) map {
    case (x,currency) => Money(x,currency)
  }

  def gatherHRN(portfilio:List[(Money,Symbol)],
                currencyBoard:CurrencyBoard):Future[Either[String,Money]] =
  {
    val state: Future[Either[String,Money]] = Future successful Right(Money(0.0,'HRN))
    xPortfilio.foldLeft(state){ (sFuture,eFuture) =>
      ???
      //
      //for( sE <- sFuture;
      //     eE <- eFuture;
      //     nE <- eitherToHrn(eE,currencyBoard)) yield eitherSum(sE,nE)
    }
  }

  def eitherToHrn(eE:Either[String,Money], currencyBoard: CurrencyBoard): Future[Either[String,Money]] = {
    eE match {
      case Left(e) => Future successful(eE)
      case Right(e) => toHrn(e,currencyBoard)
    }
  }

  def eitherSum(sE:Either[String,Money],
                eE:Either[String,Money]):Either[String,Money] = {
    //for {s <- sE
    //     e <- eE} yield Money(s.amount + e.amount, s.currency)
    //sE.flatMap(s => for{e <- eE} yield Money(s.amount + e.amount, s.currency) )
    sE.flatMap(s => eE.map(e => Money(s.amount + e.amount,s.currency)))
  }


  def toHrn(in:Money, currencyBoard: CurrencyBoard):Future[Either[String,Money]] =
    for(ex <- currencyBoard.exchange(in.currency,'HRN,in.amount)) yield {
      for(x <- ex) yield Money(x,'HRN)
    }

}
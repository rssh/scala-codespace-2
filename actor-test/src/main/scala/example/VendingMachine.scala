package example

import akka.actor.{Actor, FSM}
import akka.actor.Actor.Receive
import akka.actor.FSM.State

sealed trait VendingMachineMessage

case class PutCoin(n:Double) extends VendingMachineMessage
case class SelectBeverage(id:Long) extends VendingMachineMessage

case class Beverage(id:Long) extends VendingMachineMessage
case class ErrorMessage(message: String) extends VendingMachineMessage
case class ReturnCoin(n:Double) extends VendingMachineMessage

class VendingMachine extends Actor {

  override def receive: Receive = emptyMachine

  var balance = 0.0

  def emptyMachine: Receive =
  {
    case SelectBeverage(x) => sender()!ErrorMessage("Put money at first")
    case PutCoin(s) =>
      balance = s
      context.become(machineWithMoney)
  }

  def machineWithMoney: Receive =
  {
    case SelectBeverage(x) => if (beverageCost(x) <= balance ) {
      val rest = balance - beverageCost(x)
      if (rest > 0) {
        sender() ! ReturnCoin(rest)
      }
      sender() ! Beverage(x)
      context.unbecome()
    } else {
      sender() ! ErrorMessage("Not enough money")
    }
    case PutCoin(m) =>
      balance += m
  }


  def beverageCost(id:Long): Double = 1.0

}

sealed trait VMState
case object Empty extends VMState
case object WithMoney extends VMState

class VendingMachineFSM extends Actor with FSM[VMState,Double]
{

  startWith(Empty,0.0)
    when(Empty){
      case Event(SelectBeverage(x),balance) =>
           sender() ! ErrorMessage("Put money at first")
           State(Empty,0.0)
      case Event(PutCoin(s),balance) =>
           State(WithMoney,balance + s)
    }
    when(WithMoney) {
      case Event(SelectBeverage(x),balance) =>
        if (beverageCost(x) <= balance) {
          val rest = beverageCost(x) - balance
          if (rest > 0)
            sender ! ReturnCoin(rest)
          sender ! Beverage
          State(Empty, 0.0)
        } else {
          ErrorMessage("Not enough money")
          State(WithMoney,balance)
        }
      case Event(PutCoin(s),balance) =>
        State(WithMoney,balance + s)
    }

  def beverageCost(id:Long): Double = 1.0

}
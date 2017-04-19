package com.example.upload.server


import akka.actor.{Actor, PoisonPill, Props}

import scala.util.{Failure, Success, Try}


sealed trait ReplyMessage
case object ReplyOk extends ReplyMessage
case class ReplyError(message:String) extends ReplyMessage

class FileConsumerActor(cookie:String) extends Actor {

  case class State(
                    val filePacketConsumer: FilePacketConsumer,
                    val name:String,
                    var lastChunk: Int
                  )

  var optState: Option[State]=None

  override def receive: Receive = {
    case x@FileTransferPacket(name,chunkNumber,base64,isFinal) =>
       optState match {
         case None => processFirstPacket(x)
         case Some(state) => processContinuePacket(state,x)
       }
  }

  def processFirstPacket(x:FileTransferPacket):Unit =
  {
    if (x.chunkNumber != 1) {
      sender() ! ReplyError("first chunk number must be 1")
    }else {
      val consumer = FilePacketConsumer.create(x.name)
      val newState = State(
        filePacketConsumer = consumer,
        name = x.name,
        lastChunk = x.chunkNumber
      )
      val r = for {_ <- consumer.append(x.base64)
           _ <- handleFinal(newState, x)
              } yield ()
      r match {
        case Success(_) => sender () ! ReplyOk
        case Failure(ex) => sender() ! ReplyError(message = ex.getMessage)
      }
    }
  }

  def processContinuePacket(state:State, x:FileTransferPacket):Unit =
  {
    System.err.println(s"processContiunePacket: $x")
  }

  def handleFinal(state:State,p:FileTransferPacket): Try[Unit] =
  {
    if (p.isFinal) {
      // TODO: handle errors
      val r = state.filePacketConsumer.close()
      self ! PoisonPill
      r
    } else {
      Success(())
    }
  }

}

object FileConsumerActor
{

  def props(cookie:String):Props =
   Props(classOf[FileConsumerActor],cookie)

}
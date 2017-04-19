package com.example.upload.server


import scala.collection.mutable.ArrayBuffer
import scala.util.Try

trait FilePacketConsumer {

  def append(chunk:String):Try[Unit]

  def close():Try[Unit]

}

class SimpleFilePacketConsumer(fname:String ) extends FilePacketConsumer {

  val bytes: ArrayBuffer[Byte] = ArrayBuffer()

  override def append(chunk: String): Try[Unit] =
  {
   Try {
     val decoder = java.util.Base64.getDecoder
     val newBytes = decoder.decode(chunk)
     bytes.append(newBytes: _*)
   }
  }

  override def close(): Unit =
  {
    Try {
      //TODO: save
      System.err.println(s"closeing, received: ${bytes.size} bytes")
    }
  }

}

object FilePacketConsumer
{

  def create(fname: String):FilePacketConsumer =
      new SimpleFilePacketConsumer(fname)

}

package com.example

import java.nio.ByteOrder
import java.nio.file.Paths

import akka.Done
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._

import scala.concurrent._
import scala.util.{Failure, Success}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  val file = Paths.get("testdata", "data.bin")
  val source = FileIO.fromPath(file)

  def computeSize(offsetBytes: Array[Byte], u: Int): Int = {
    val s = SimpleHeader.frameSize(u).getOrElse(0)
    println(f"Num offset bytes: ${offsetBytes.length}, u: $u%X ($u%d), frame size: $s%d")
    s - 4
  }

  val framer = Framing.lengthField(fieldLength = 4, fieldOffset = 0, maximumFrameLength = 16,
    byteOrder = ByteOrder.BIG_ENDIAN, computeFrameSize = computeSize)

  val done: Future[Done] = source.via(framer).runForeach(bs => println(s"bytes in frame: ${bs.length}"))

  done.onComplete {
    case Failure(exception) =>
      println(s"Failed with ${exception.getMessage}")
      system.terminate()
    case Success(value) =>
      println(s"Success: $value")
      system.terminate()
  }
}

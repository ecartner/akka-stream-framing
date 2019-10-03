package com.example

object SimpleHeader {
  def apply(u: Int): SimpleHeader = new SimpleHeader(u)

  def fromInt(u: Int): Option[SimpleHeader] = {
    val h = SimpleHeader(u)
    Option.when(h.isValid)(h)
  }

  def frameSize(u: Int): Option[Int] = fromInt(u).map(_.frameSize)
}

class SimpleHeader(u: Int) {

  private val syncWord = u >>> 20 & 0xFFF
  private val fieldA = (u >>> 19 & 1).toByte

  def isValid: Boolean = syncWord == 0xFFF

  def frameSize: Int = if (fieldA == 1) 16 else 8
}

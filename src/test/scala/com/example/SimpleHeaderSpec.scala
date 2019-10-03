package com.example

import org.scalatest.{FlatSpec, Matchers}

class SimpleHeaderSpec extends FlatSpec with Matchers {

  "A SimpleHeader" should "be four bytes starting with 0xFFF" in {
    SimpleHeader(0xFFFD0123).isValid shouldBe true
    SimpleHeader(0xFFFFFFFF).isValid shouldBe true
    SimpleHeader(0xFFFFFFF).isValid shouldBe false
    SimpleHeader(0xFFEFFFFF).isValid shouldBe false
  }

  it should "have a frame size of 8 bytes if bit 19 is clear" in {
    SimpleHeader(0xFFF7FFFF).frameSize shouldBe 8
    SimpleHeader(0xFFF00000).frameSize shouldBe 8
  }

  it should "have a frame size of 16 bytes if bit 19 is set" in {
    SimpleHeader(0xFFFFFFFF).frameSize shouldBe 16
    SimpleHeader(0xFFF80000).frameSize shouldBe 16
  }

  "fromInt" should "return None if the given value isn't a valid header" in {
    SimpleHeader.fromInt(0xFFFFFFF) shouldBe None
    SimpleHeader.fromInt(0xFFEFFFFF) shouldBe None
    SimpleHeader.fromInt(0) shouldBe None
  }

  it should "Return Some(SimpleHeader) if the given value is a valid header" in {
    SimpleHeader.fromInt(0xFFFFFFFF) shouldBe defined
    SimpleHeader.fromInt(0xFFF00000) shouldBe defined
  }
}

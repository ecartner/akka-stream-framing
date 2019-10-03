A simple project to demonstrate unexpected behavior from Akka Streams Framing.

Explanation 
-----------

The example data file `testdata/data.bin` contains 56 bytes of a simple, hypothetical framed data format. Frames may either be 8 or 16 bytes in length. Every frame contains a four byte header. Every header starts with a sync word of 0xFFF. The method `SimpleHeader.frameSize()` will calculate the frame size (including the four bytes of the header) for a given header. The four bytes of the header are passed to `frameSize()` as a 32 bit big endian signed integer value.

`Main` opens the example data file and treats it as a source for a stream of bytes. The stream is passed through the flow `framer` which _should_ spit out a stream of `ByteString`s, with each byte string containing one frame.  However, the framing operation fails on the first frame.

`framer` uses `akka.streams.scaladsl.Framing.lengthField` to perform the framing. It passes in a function `computeSize` which receives the four bytes of the header and returns the number of bytes remaining in the frame after the header. Console output inside `computeSize` indicates it gets called with the expected values and it returns the correct frame size.

The failure happens because `Framing.lengthField()` is also looking at the value of the header as a 32 bit signed integer. Because every header starts with 0xFFF, `lenghField()` it sees a negative value and decides it is an invalid frame length.

Usage
-----

A simple `> sbt run` should demonstrate this behavior.



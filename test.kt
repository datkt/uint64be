import datkt.uint64be.encodingLength
import datkt.uint64be.UINT_32_MAX
import datkt.uint64be.encode
import datkt.uint64be.decode

import datkt.tape.collect
import datkt.tape.Test
import datkt.tape.test

import kotlin.math.pow

val MAX_SAFE_INT = ((2.0).pow(53) - 1.0).toLong()
val table = "0123456789abcdef".toCharArray()

fun toHexString(bytes: ByteArray): String {
  var output = CharArray(2 * bytes.size)
  for (i in bytes.indices) {
    val j = (bytes[i].toInt() and 0xff).toInt()
    output[2 * i] = table[j ushr 4]
    output[1 + 2 * i] = table[j and 0x0f]
  }
  return String(output)
}

fun bufferFrom(vararg bytes: Number): ByteArray {
  return ByteArray(bytes.count()) { i -> bytes[i].toByte() }
}

fun main(args: Array<String>) {
  test("UINT_32_MAX == (2.0).pow(32)", fun(t: Test) {
    t.equal(UINT_32_MAX, (2.0).pow(32).toLong())
    t.end()
  })

  test("encodingLength(): Int", fun(t: Test) {
    t.equal(8, encodingLength())
    t.end()
  })

  test("encode(int: Number): ByteArray", fun(t: Test) {
    t.equal(toHexString(encode(UINT_32_MAX + 1)), "0000000100000001")
    t.equal(toHexString(encode(42424242424242)), "00002695a9e649b2")
    t.equal(toHexString(encode(MAX_SAFE_INT)), "001fffffffffffff")
    t.equal(toHexString(encode(UINT_32_MAX)), "0000000100000000")
    t.equal(toHexString(encode(0xdeadface)), "00000000deadface")
    t.equal(toHexString(encode(0xfeed)), "000000000000feed")
    t.equal(toHexString(encode(1111)), "0000000000000457")
    t.equal(toHexString(encode(42)), "000000000000002a")
    t.equal(toHexString(encode(0)), "0000000000000000")
    t.end()
  })

  test("decode(buffer: ByteArray, offset: UInt = 0u): Byte", fun(t: Test) {
    t.equal(decode(bufferFrom(0,0,0,1,0,0,0,0)), (2.0).pow(32).toLong())
    t.equal(decode(bufferFrom(0,0,0,1,0,0,0,1)), 1+(2.0).pow(32).toLong())
    t.equal(decode(bufferFrom(0,0,0,0,0,0,0,42)), 42.toLong())
    t.equal(decode(encode(42)), 42.toLong())

    t.equal(
      decode(bufferFrom(0x00, 0x00, 0x26, 0x95, 0xa9, 0xe6, 0x49, 0xb2)),
      42424242424242.toLong())

    t.equal(
      decode(bufferFrom(0x0, 0x1f, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff)),
      MAX_SAFE_INT
    )

    t.end()
  })

  collect()
}

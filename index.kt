package datkt.uint64be

import kotlin.math.floor
import kotlin.math.pow

/**
 * Largest 32-bit integer value.
 */
val UINT_32_MAX = (2.0).pow(32).toLong()

private fun writeUInt32BE(buffer: ByteArray, value: Long, offset: Int) {
  val byte = value.toInt()
  buffer[offset + 0] = (value shr 24 and 0xff).toByte()
  buffer[offset + 1] = (value shr 16 and 0xff).toByte()
  buffer[offset + 2] = (value shr 8 and 0xff).toByte()
  buffer[offset + 3] = (value and 0xff).toByte()
}

private fun readUInt32BE(buffer: ByteArray, offset: Int): Long {
  val byte = (
      (buffer[offset + 0].toInt() and 0xff) * (2.0.pow(24).toLong())
    + (buffer[offset + 1].toInt() and 0xff) * (2.0.pow(16).toLong())
    + (buffer[offset + 2].toInt() and 0xff) * (2.0.pow(8).toLong())
    + (buffer[offset + 3].toInt() and 0xff)
  )
  return byte
}

/**
 * Inline function to return constant length of 8 bytes
 */

inline fun encodingLength(): Int = 8

/**
 * Encodes an unsigned 64 bit integer number into
 * a ByteArray.
 */
fun encode(int: Number): ByteArray {
  val buffer = ByteArray(8)
  val max = UINT_32_MAX
  val num = int.toLong()
  val top = num / max
  val rem = num - top * max

  writeUInt32BE(buffer, top, 0)
  writeUInt32BE(buffer, rem, 4)
  return buffer
}

/**
 * Decodes an unsigned 64 bit integer from a ByteArray
 * into a Long.
 */
fun decode(buffer: ByteArray, offset: Int = 0): Long {
  val off = offset.toInt()
  val max = UINT_32_MAX
  val top = readUInt32BE(buffer, off)
  val rem = readUInt32BE(buffer, off + 4)
  return top * max + rem
}

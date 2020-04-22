package me.clutchy.server.extensions

import java.nio.ByteBuffer

fun Int.toHex(): String = "0x%02x".format(this)

fun Int.byteArray(): ByteArray = ByteBuffer.allocate(Int.SIZE_BYTES).putInt(this).array()

fun Int.varInt(): ByteArray {
    var array = byteArrayOf()
    var value = this
    while (value and -128 != 0) {
        array += (value and 127 or 128).toByte()
        value = value ushr 7
    }
    array += value.toByte()
    return array
}

package me.clutchy.server.extensions

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.EOFException
import kotlin.experimental.and

fun DataInputStream.readPacket(): DataInputStream? {
    val length = this.varInt()
    if (length <= 0) {
        return null
    }
    val data = ByteArray(length)
    try {
        this.readFully(data)
    } catch (e: Exception) {
        return null
    }
    // "Data: ${data.toList()}".print()
    return DataInputStream(ByteArrayInputStream(data))
}

fun DataInputStream.varInt(): Int {
    var numRead = 0
    var result = 0
    var read: Byte
    do {
        read = this.read().toByte()
        val value: Int = (read and 0b01111111).toInt()
        result = result or (value shl 7 * numRead)
        numRead++
        if (numRead > 5) return 0
    } while ((read and 0b10000000.toByte()).toInt() != 0)
    return result
}

fun DataInputStream.byteArray(): ByteArray {
    val buf = ByteArray(this.varInt())
    try {
        this.readFully(buf)
    } catch (e: EOFException) {
        e.printStackTrace()
        return byteArray()
    }
    return buf
}

fun DataInputStream.string(): String {
    return String(byteArray(), Charsets.UTF_8)
}

fun DataInputStream.short(): Short {
    return try {
        this.readShort()
    } catch (e: EOFException) {
        0
    }
}

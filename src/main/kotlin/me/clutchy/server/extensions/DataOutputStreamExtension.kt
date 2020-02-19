package me.clutchy.server.extensions

import java.io.DataOutputStream
import kotlin.experimental.or

fun DataOutputStream.varInt(int: Int) {
    var value = int
    do {
        var temp: Byte = (value and 0b01111111).toByte()
        value = value ushr 7
        if (value != 0) {
            temp = (temp or 0b10000000.toByte())
        }
        this.writeByte(temp.toInt())
    } while (value != 0)
}


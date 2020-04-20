package me.clutchy.server.extensions

fun Long.byteArray(): ByteArray {
    return byteArrayOf(this.toByte())
}

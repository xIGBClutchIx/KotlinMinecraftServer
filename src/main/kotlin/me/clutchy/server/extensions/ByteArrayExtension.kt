package me.clutchy.server.extensions

fun ByteArray.varInt(): ByteArray {
    return this.size.varInt() + this
}

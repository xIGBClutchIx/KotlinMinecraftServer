package me.clutchy.server.extensions

fun ByteArray.varInt(): ByteArray {
    val finalArray: ByteArray = this
    return finalArray.size.varInt() + finalArray
}

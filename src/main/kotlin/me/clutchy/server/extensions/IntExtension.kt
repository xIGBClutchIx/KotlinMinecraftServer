package me.clutchy.server.extensions

fun Int.toHex(): String = "0x%02x".format(this)

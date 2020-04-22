package me.clutchy.server.extensions

fun ByteArray.varInt(): ByteArray = this.size.varInt() + this

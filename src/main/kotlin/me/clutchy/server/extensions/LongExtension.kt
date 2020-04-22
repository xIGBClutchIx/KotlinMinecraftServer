package me.clutchy.server.extensions

import java.nio.ByteBuffer

fun Long.byteArray(): ByteArray = ByteBuffer.allocate(Long.SIZE_BYTES).putLong(this).array()

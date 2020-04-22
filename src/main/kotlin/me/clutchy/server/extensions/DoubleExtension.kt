package me.clutchy.server.extensions

import java.nio.ByteBuffer

fun Double.byteArray(): ByteArray = ByteBuffer.allocate(Long.SIZE_BYTES).putDouble(this).array()

package me.clutchy.server.extensions

import java.nio.ByteBuffer

fun Float.byteArray(): ByteArray = ByteBuffer.allocate(Int.SIZE_BYTES).putFloat(this).array()

package me.clutchy.server.packets.client

import me.clutchy.server.packets.ClientPacket

class PongPacket(private val payload: Long): ClientPacket(1) {

    override fun getData(): ByteArray {
        return byteArrayOf(payload.toByte())
    }
}

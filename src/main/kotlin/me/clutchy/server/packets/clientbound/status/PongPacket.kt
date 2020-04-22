package me.clutchy.server.packets.clientbound.status

import me.clutchy.server.extensions.byteArray
import me.clutchy.server.packets.ClientPacket

class PongPacket(private val payload: Long): ClientPacket(0x01) {

    override fun getData(): ByteArray {
        return payload.byteArray()
    }
}

package me.clutchy.server.packets.clientbound.play

import me.clutchy.server.extensions.byteArray
import me.clutchy.server.packets.ClientPacket
import kotlin.random.Random

class KeepAlivePacket: ClientPacket(0x21) {

    override fun getData(): ByteArray {
        return Random.nextLong().byteArray()
    }
}

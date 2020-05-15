package me.clutchy.server.packets.clientbound.login

import me.clutchy.server.packets.ClientPacket

class DisconnectLoginPacket(private val message: String): ClientPacket(0x00) {

    override fun getData(): ByteArray {
        return "{\"text\":\"$message\",\"color\":\"dark_red\"}".toByteArray(Charsets.UTF_8)
    }
}

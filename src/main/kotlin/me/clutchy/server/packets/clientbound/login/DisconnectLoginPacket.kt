package me.clutchy.server.packets.clientbound.login

import me.clutchy.server.packets.ClientPacket

class DisconnectLoginPacket: ClientPacket(0x00) {

    override fun getData(): ByteArray {
        return "{\"text\":\"Get yeeted\",\"color\":\"dark_aqua\"}".toByteArray(Charsets.UTF_8)
    }
}

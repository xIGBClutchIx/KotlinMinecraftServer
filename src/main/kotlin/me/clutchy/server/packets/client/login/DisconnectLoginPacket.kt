package me.clutchy.server.packets.client.login

import me.clutchy.server.packets.ClientPacket

class DisconnectLoginPacket: ClientPacket(0) {

    override fun getData(): ByteArray {
        return "{\"text\":\"Get yeeted\",\"color\":\"dark_aqua\"}".toByteArray(Charsets.UTF_8)
    }
}

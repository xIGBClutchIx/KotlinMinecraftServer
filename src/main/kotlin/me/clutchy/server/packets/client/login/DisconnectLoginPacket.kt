package me.clutchy.server.packets.client.login

import me.clutchy.server.packets.ClientPacket
import java.nio.charset.StandardCharsets

class DisconnectLoginPacket: ClientPacket(0) {

    override fun getData(): ByteArray {
        return "{\"text\":\"Get yeeted\",\"color\":\"dark_aqua\"}".toByteArray(StandardCharsets.UTF_8)
    }
}

package me.clutchy.server.packets.serverbound.play

import me.clutchy.server.extensions.string
import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.serverbound.PacketInfo
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import java.io.DataInputStream

@PacketInfo(ServerPacketHandler.ConnectionState.PLAY, 0x0B)
class PluginMessagePacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var channel: String = ""
        private set

    var viewDistance: ByteArray = byteArrayOf()
        private set

    init {
        channel = data.string()
        viewDistance = data.readBytes()
    }
}


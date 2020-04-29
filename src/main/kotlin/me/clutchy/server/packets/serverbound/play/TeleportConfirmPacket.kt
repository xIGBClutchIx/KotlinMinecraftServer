package me.clutchy.server.packets.serverbound.play

import me.clutchy.server.extensions.varInt
import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.serverbound.PacketInfo
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import java.io.DataInputStream

@PacketInfo(ServerPacketHandler.ConnectionState.PLAY, 0x00)
class TeleportConfirmPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var teleportID: Int = 0
        private set

    init {
        teleportID = data.varInt()
    }
}

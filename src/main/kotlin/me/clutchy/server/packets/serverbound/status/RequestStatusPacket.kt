package me.clutchy.server.packets.serverbound.status

import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.serverbound.PacketInfo
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import me.clutchy.server.packets.clientbound.status.ResponsePacket
import java.io.DataInputStream

@PacketInfo(ServerPacketHandler.ConnectionState.STATUS, 0x00)
class RequestStatusPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    init {
        connection.send(ResponsePacket(connection.protocolVersion))
    }
}

package me.clutchy.server.packets.serverbound.status

import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.clientbound.status.ResponsePacket
import java.io.DataInputStream

class RequestStatusPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    init {
        connection.send(ResponsePacket())
    }
}

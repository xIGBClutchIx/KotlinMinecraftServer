package me.clutchy.server.packets.server.status

import me.clutchy.server.SocketConnection
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.client.status.ResponsePacket
import java.io.DataInputStream

class RequestStatusPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    init {
        connection.send(ResponsePacket())
    }
}

package me.clutchy.server.packets.server

import me.clutchy.server.SocketConnection
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.client.PongPacket
import java.io.DataInputStream

class PingPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var payload: Long = 0
        private set

    init {
        payload = data.readLong()
        connection.send(PongPacket(payload))
    }
}

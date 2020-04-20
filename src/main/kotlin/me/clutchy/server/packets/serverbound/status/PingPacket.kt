package me.clutchy.server.packets.serverbound.status

import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.clientbound.status.PongPacket
import java.io.DataInputStream

class PingPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var payload: Long = 0
        private set

    init {
        payload = data.readLong()
        connection.send(PongPacket(payload))
    }
}

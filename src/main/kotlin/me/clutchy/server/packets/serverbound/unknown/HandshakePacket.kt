package me.clutchy.server.packets.serverbound.unknown

import me.clutchy.server.network.SocketConnection
import me.clutchy.server.extensions.short
import me.clutchy.server.extensions.string
import me.clutchy.server.extensions.varInt
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import java.io.DataInputStream

class HandshakePacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var protocolVersion: Int = 0
        private set

    var serverAddress: String = ""
        private set

    var serverPort: Short = 0
        private set

    init {
        protocolVersion = data.varInt()
        serverAddress = data.string()
        serverPort = data.short()
        connection.protocolVersion = protocolVersion
        ServerPacketHandler.setState(connection, data.varInt())
    }
}

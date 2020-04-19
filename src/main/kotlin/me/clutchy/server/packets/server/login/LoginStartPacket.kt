package me.clutchy.server.packets.server.login

import me.clutchy.server.Server
import me.clutchy.server.SocketConnection
import me.clutchy.server.extensions.string
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import me.clutchy.server.packets.client.login.EncryptionRequestPacket
import me.clutchy.server.packets.client.login.LoginPacket
import java.io.DataInputStream

class LoginStartPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var username: String = ""
        private set

    init {
        username = data.string()
        if (Server.encryption) {
            connection.send(EncryptionRequestPacket())
        } else {
            connection.send(LoginPacket(username))
            ServerPacketHandler.setState(connection, 3)
        }
    }
}

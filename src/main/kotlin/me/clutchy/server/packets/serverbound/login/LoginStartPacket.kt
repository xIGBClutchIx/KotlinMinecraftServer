package me.clutchy.server.packets.serverbound.login

import me.clutchy.server.network.Server
import me.clutchy.server.network.SocketConnection
import me.clutchy.server.extensions.string
import me.clutchy.server.packets.serverbound.PacketInfo
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import me.clutchy.server.packets.clientbound.login.EncryptionRequestPacket
import me.clutchy.server.packets.clientbound.login.LoginSuccessPacket
import java.io.DataInputStream

@PacketInfo(ServerPacketHandler.ConnectionState.LOGIN, 0x00)
class LoginStartPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var username: String = ""
        private set

    init {
        username = data.string()
        if (Server.encryption) {
            connection.send(EncryptionRequestPacket())
        } else {
            connection.send(LoginSuccessPacket(username))
            ServerPacketHandler.setState(connection, 3)
        }
    }
}

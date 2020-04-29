package me.clutchy.server.packets.serverbound.play

import me.clutchy.server.extensions.string
import me.clutchy.server.extensions.varInt
import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.serverbound.PacketInfo
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import java.io.DataInputStream

@PacketInfo(ServerPacketHandler.ConnectionState.PLAY, 0x05)
class ClientSettingsPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var locale: String = ""
        private set

    var viewDistance: Byte = 0x00
        private set

    var chatMode: Int = 0
        private set

    var chatColors: Boolean = true
        private set

    var displayedSkinParts: Int = 0
        private set

    var mainHand: Int = 1
        private set

    init {
        locale = data.string()
        viewDistance = data.readByte()
        chatMode = data.varInt()
        chatColors = data.readBoolean()
        displayedSkinParts = data.readUnsignedByte()
        mainHand = data.varInt()
    }
}

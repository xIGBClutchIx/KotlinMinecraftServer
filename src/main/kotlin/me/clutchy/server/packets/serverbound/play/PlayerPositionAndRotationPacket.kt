package me.clutchy.server.packets.serverbound.play

import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.serverbound.PacketInfo
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.ServerPacketHandler
import java.io.DataInputStream

@PacketInfo(ServerPacketHandler.ConnectionState.PLAY, 0x12)
class PlayerPositionAndRotationPacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var x: Double = 0.0
        private set

    var yFeet: Double = 0.0
        private set

    var z: Double = 0.0
        private set

    var yaw: Float = 0.0F
        private set

    var pitch: Float = 0.0F
        private set

    var onGround: Boolean = true
        private set

    init {
        x = data.readDouble()
        yFeet = data.readDouble()
        z = data.readDouble()
        yaw = data.readFloat()
        pitch = data.readFloat()
        onGround = data.readBoolean()
    }
}

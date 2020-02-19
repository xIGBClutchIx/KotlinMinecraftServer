package me.clutchy.server.packets

import me.clutchy.server.SocketConnection
import me.clutchy.server.packets.server.HandshakePacket
import me.clutchy.server.packets.server.PingPacket
import java.io.DataInputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class ServerPacketHandler {

    companion object {
        private val packets: Map<Int, KClass<out ServerPacket>> = mapOf(
                0 to HandshakePacket::class,
                1 to PingPacket::class
        )

        fun managePacket(packetID: Int, data: DataInputStream, connection: SocketConnection): ServerPacket? {
            val packet = packets[packetID]
            return if (packet != null) {
                packet.primaryConstructor?.call(data, connection)
            } else {
                null
            }
        }
    }
}

package me.clutchy.server.packets

import me.clutchy.server.network.SocketConnection
import me.clutchy.server.extensions.print
import me.clutchy.server.packets.server.login.LoginStartPacket
import me.clutchy.server.packets.server.status.PingPacket
import me.clutchy.server.packets.server.status.RequestStatusPacket
import me.clutchy.server.packets.server.unknown.UnknownHandshakePacket
import java.io.DataInputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class ServerPacketHandler {

    companion object {
        val stateHandler = hashMapOf<SocketConnection, ConnectionState>()

        private val serverPackets: Map<ConnectionState, Map<Int, KClass<out ServerPacket>>> = mapOf(
                ConnectionState.UNKNOWN to mapOf(
                        0 to UnknownHandshakePacket::class
                ),
                ConnectionState.STATUS to mapOf(
                        0 to RequestStatusPacket::class,
                        1 to PingPacket::class
                ),
                ConnectionState.LOGIN to mapOf(
                        0 to LoginStartPacket::class
                )
        )

        fun managePacket(packetID: Int, data: DataInputStream, connection: SocketConnection) {
            val state = stateHandler.getOrDefault(connection, ConnectionState.UNKNOWN)
            val packet = serverPackets[state]?.get(packetID)
            if (packet != null) packet.primaryConstructor?.call(data, connection)
        }

        fun setState(connection: SocketConnection, state: Int) {
            stateHandler[connection] = ConnectionState.values()[state]
            "(${connection.address}) = ${stateHandler[connection]}".print()
        }
    }

    enum class ConnectionState {
        UNKNOWN,
        STATUS,
        LOGIN,
        PLAY
    }
}

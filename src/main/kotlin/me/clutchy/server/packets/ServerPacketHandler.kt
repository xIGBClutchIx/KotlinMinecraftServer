package me.clutchy.server.packets

import me.clutchy.server.extensions.print
import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.clientbound.play.ChatMessagePacket
import me.clutchy.server.packets.clientbound.play.JoinGamePacket
import me.clutchy.server.packets.clientbound.play.KeepAlivePacket
import me.clutchy.server.packets.clientbound.play.PlayerPositionAndLookPacket
import me.clutchy.server.packets.serverbound.login.LoginStartPacket
import me.clutchy.server.packets.serverbound.status.PingPacket
import me.clutchy.server.packets.serverbound.status.RequestStatusPacket
import me.clutchy.server.packets.serverbound.unknown.UnknownHandshakePacket
import java.io.DataInputStream
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


class ServerPacketHandler {

    companion object {
        val stateHandler = hashMapOf<SocketConnection, ConnectionState>()
        private val timer = Timer("Keep-Alive")

        init {
            timer.scheduleAtFixedRate(object: TimerTask() {
                override fun run() {
                    for ((connection, state) in stateHandler) {
                        if (state == ConnectionState.PLAY) {
                            connection.send(KeepAlivePacket())
                        }
                    }
                }
            }, 0, 5000)
        }

        private val serverPackets: Map<ConnectionState, Map<Int, KClass<out ServerPacket>>> = mapOf(
                ConnectionState.UNKNOWN to mapOf(
                        0x00 to UnknownHandshakePacket::class
                ),
                ConnectionState.STATUS to mapOf(
                        0x00 to RequestStatusPacket::class,
                        0x01 to PingPacket::class
                ),
                ConnectionState.LOGIN to mapOf(
                        0x00 to LoginStartPacket::class
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
            // Start play
            if (ConnectionState.values()[state] == ConnectionState.PLAY) {
                connection.send(JoinGamePacket())
                connection.send(PlayerPositionAndLookPacket())
                connection.send(ChatMessagePacket())
            }
        }
    }

    enum class ConnectionState {
        UNKNOWN,
        STATUS,
        LOGIN,
        PLAY
    }
}

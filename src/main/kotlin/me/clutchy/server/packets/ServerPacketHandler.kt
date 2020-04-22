package me.clutchy.server.packets

import me.clutchy.server.extensions.print
import me.clutchy.server.extensions.toHex
import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.clientbound.login.DisconnectLoginPacket
import me.clutchy.server.packets.clientbound.login.EncryptionRequestPacket
import me.clutchy.server.packets.clientbound.login.LoginSuccessPacket
import me.clutchy.server.packets.clientbound.play.ChatMessagePacket
import me.clutchy.server.packets.clientbound.play.JoinGamePacket
import me.clutchy.server.packets.clientbound.play.KeepAlivePacket
import me.clutchy.server.packets.clientbound.play.PlayerPositionAndLookPacket
import me.clutchy.server.packets.clientbound.status.PongPacket
import me.clutchy.server.packets.clientbound.status.ResponsePacket
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

        private val clientToServerPackets: Map<ConnectionState, Map<Int, KClass<out ServerPacket>>> = mapOf(
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

        private val serverToClientPackets: Map<ConnectionState, Map<Int, KClass<out ClientPacket>>> = mapOf(
                ConnectionState.STATUS to mapOf(
                        0x00 to ResponsePacket::class,
                        0x01 to PongPacket::class
                ),
                ConnectionState.LOGIN to mapOf(
                        0x00 to DisconnectLoginPacket::class,
                        0x00 to EncryptionRequestPacket::class,
                        0x00 to LoginSuccessPacket::class
                ),
                ConnectionState.PLAY to mapOf(
                        0x0F to ChatMessagePacket::class,
                        0x21 to KeepAlivePacket::class,
                        0x26 to JoinGamePacket::class,
                        0x36 to PlayerPositionAndLookPacket::class
                )
        )

        fun managePacket(packetID: Int, data: DataInputStream, connection: SocketConnection) {
            val state = stateHandler.getOrDefault(connection, ConnectionState.UNKNOWN)
            val packet = clientToServerPackets[state]?.get(packetID)
            var packetName = "Unknown"
            if (packet != null) {
                packetName = packet.simpleName.toString().removeSuffix("Packet").replace(Regex("(.)([A-Z])"), "$1 $2")
                packet.primaryConstructor?.call(data, connection)
            }
            "(${connection.address}) -> ${packetID.toHex()} [$packetName]".print()
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

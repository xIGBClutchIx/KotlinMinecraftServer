package me.clutchy.server.packets

import me.clutchy.server.extensions.toHex
import me.clutchy.server.network.SocketConnection
import me.clutchy.server.packets.clientbound.play.ChatMessagePacket
import me.clutchy.server.packets.clientbound.play.JoinGamePacket
import me.clutchy.server.packets.clientbound.play.KeepAlivePacket
import me.clutchy.server.packets.clientbound.play.PlayerPositionAndLookPacket
import me.clutchy.server.packets.serverbound.PacketInfo
import java.io.DataInputStream
import java.io.File
import java.net.URL
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class ServerPacketHandler {

    companion object {
        val stateHandler = hashMapOf<SocketConnection, ConnectionState>()
        private val timer = Timer("Keep-Alive")
        private val serverboundPackets: HashMap<ConnectionState, HashMap<Int, KClass<ServerPacket>>> = hashMapOf()

        init {
            timer.scheduleAtFixedRate(object: TimerTask() {
                override fun run() {
                    for ((connection, state) in stateHandler) {
                        if (state == ConnectionState.PLAY) {
                            connection.send(KeepAlivePacket())
                        }
                    }
                }
            }, 0, 10000)
        }

        fun managePacket(packetID: Int, data: DataInputStream, connection: SocketConnection) {
            val state = stateHandler.getOrDefault(connection, ConnectionState.UNKNOWN)
            val packet = serverboundPackets[state]?.get(packetID)
            val message = "(${packetID.toHex()}) ["
            var packetName = "Unknown"
            if (packet != null) {
                packetName = packet.simpleName.toString().removeSuffix("Packet").replace(Regex("(.)([A-Z])"), "$1 $2")
                connection.printSide(SocketConnection.PrintSide.CLIENT_TO_SERVER, SocketConnection.t.brightGreen("$message$packetName]"))
                packet.primaryConstructor?.call(data, connection)
            } else {
                connection.printSide(SocketConnection.PrintSide.CLIENT_TO_SERVER, SocketConnection.t.magenta("$message$packetName]"))
            }
        }

        fun setState(connection: SocketConnection, state: Int) {
            stateHandler[connection] = ConnectionState.values()[state]
            connection.printSide(SocketConnection.PrintSide.SIDE, SocketConnection.t.yellow(ConnectionState.values()[state].name))
            // Start play
            if (ConnectionState.values()[state] == ConnectionState.PLAY) startGame(connection)
        }

        private fun startGame(connection: SocketConnection) {
            connection.send(JoinGamePacket())
            connection.send(PlayerPositionAndLookPacket())
            connection.send(ChatMessagePacket())
        }

        fun registerServerboundPackets() {
            // Register all states
            for (state in ConnectionState.values()) serverboundPackets[state] = hashMapOf()
            // Get serverbound package
            val packageName = PacketInfo::class.java.packageName
            var name = packageName
            // Search for classes
            if (!name.startsWith("/")) name = "/$name"
            name = name.replace('.', '/')
            val url: URL = ServerPacketHandler::class.java.getResource(name)
            val directory = File(url.file)
            if (!directory.exists()) return
            directory.walk().filter { f -> f.isFile && !f.name.contains('$') && f.name.endsWith(".class") }.forEach {
                val fullyQualifiedClassName = packageName + it.canonicalPath.removePrefix(directory.canonicalPath).dropLast(6).replace('/', '.').replace("\\", ".")
                val clazz = Class.forName(fullyQualifiedClassName)
                val filter = clazz.kotlin.annotations.filterIsInstance<PacketInfo>()
                if (clazz.kotlin.annotations.isNotEmpty() && filter.isNotEmpty()) {
                    serverboundPackets[filter.first().state]?.put(filter.first().packetID, clazz.kotlin as KClass<ServerPacket>)
                }
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

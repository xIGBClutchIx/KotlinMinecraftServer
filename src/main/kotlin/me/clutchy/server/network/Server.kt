package me.clutchy.server.network

import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicInteger

class Server {

    companion object {
        private lateinit var socket: ServerSocket
        private val connections = hashMapOf<Socket, Thread>()
        val entityCounter = AtomicInteger()
        var encryption = false

        fun start(port: Int) {
            socket = ServerSocket(port)
            while (true) try {
                val connection: Socket = socket.accept()
                connections[connection] = Thread(SocketConnection(connection))
                connections[connection]?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun removeConnection(connection: Socket) {
            connections.remove(connection)
        }
    }
}

package me.clutchy.server

import java.net.ServerSocket
import java.net.Socket

class Server {

    companion object {
        private lateinit var socket: ServerSocket
        private val connections = hashMapOf<Socket, Thread>()

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

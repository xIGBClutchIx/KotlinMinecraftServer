package me.clutchy.server

import me.clutchy.server.network.Server
import me.clutchy.server.network.SocketConnection
import java.time.LocalDateTime

fun main() {
    println(SocketConnection.t.gray("[") +
            SocketConnection.t.cyan(LocalDateTime.now().format(SocketConnection.formatter)) + SocketConnection.t.gray("] ") +
            SocketConnection.t.bold("Starting Server"))
    Server.start(12345)
}

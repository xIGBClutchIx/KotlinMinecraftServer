package me.clutchy.server

import me.clutchy.server.extensions.print
import me.clutchy.server.network.Server

fun main() {
    "Starting Server".print()
    Server.start(12345)
}

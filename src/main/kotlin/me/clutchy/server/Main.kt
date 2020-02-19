package me.clutchy.server

import me.clutchy.server.extensions.print

fun main() {
    "Starting Server".print()
    Server.start(12345)
}

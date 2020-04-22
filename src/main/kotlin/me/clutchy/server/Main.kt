package me.clutchy.server

import com.github.ajalt.mordant.TermColors
import me.clutchy.server.extensions.print
import me.clutchy.server.network.Server

fun main() {
    TermColors().bold("Starting Server").print()
    Server.start(12345)
}

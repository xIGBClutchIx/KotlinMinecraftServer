package me.clutchy.server.extensions

import com.github.ajalt.mordant.TermColors
import me.clutchy.server.network.Server
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")

private val t = TermColors()

fun Any.print() = println(t.gray("[") + t.cyan(LocalDateTime.now().format(formatter)) + t.gray("] ") + toString())

package me.clutchy.server.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")

fun Any.print() {
    val current = LocalDateTime.now()
    val formatted = current.format(formatter)
    println("[$formatted] ${toString()}")
}

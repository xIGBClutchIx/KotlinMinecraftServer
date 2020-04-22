package me.clutchy.server.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")

fun Any.print() = println("[${LocalDateTime.now().format(formatter)}] ${toString()}")

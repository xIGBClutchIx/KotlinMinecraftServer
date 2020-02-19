package me.clutchy.server.packets

import me.clutchy.server.SocketConnection
import java.io.DataInputStream

abstract class ServerPacket(val data: DataInputStream, val connection: SocketConnection)

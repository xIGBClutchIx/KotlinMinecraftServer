package me.clutchy.server.packets

import me.clutchy.server.network.SocketConnection
import java.io.DataInputStream

abstract class ServerPacket(val data: DataInputStream, val connection: SocketConnection)

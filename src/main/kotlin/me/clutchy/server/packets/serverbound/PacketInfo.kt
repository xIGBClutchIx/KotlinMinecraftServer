package me.clutchy.server.packets.serverbound

import me.clutchy.server.packets.ServerPacketHandler

annotation class PacketInfo(val state: ServerPacketHandler.ConnectionState, val packetID: Int)
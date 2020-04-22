package me.clutchy.server.packets.clientbound.play

import me.clutchy.server.extensions.byteArray
import me.clutchy.server.extensions.varInt
import me.clutchy.server.network.Server
import me.clutchy.server.packets.ClientPacket
import java.util.*

class JoinGamePacket: ClientPacket(0x26) {

    override fun getData(): ByteArray {
        // Entity ID - Int
        var array = Server.entityCounter.incrementAndGet().byteArray()
        // Gamemode - Byte
        array += byteArrayOf(0x00)
        // Dimension - Int
        array += 0.byteArray()
        // Hashed seed - Long
        array += Random().nextLong().byteArray()
        // Max Players - Byte
        // Unused - Should still send it?
        array += byteArrayOf(64.toByte())
        // Level Type - UTF/String
        array += "default".byteArray(16)
        // View Distance - VarInt
        array += 16.varInt()
        // Reduced Debug Info - Boolean
        array += 0x00
        // Enable respawn screen - Boolean
        array += 0x01
        return array
    }
}

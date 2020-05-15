package me.clutchy.server.packets.clientbound.play

import me.clutchy.server.packets.ClientPacket

class ChunkDataPacket: ClientPacket(0x22) {

    private var x: Int = 0
    private var y: Int = 0
    private var fullChunk: Boolean = false

    override fun getData(): ByteArray {
        var array = byteArrayOf()
        return array
    }
}

package me.clutchy.server.packets

import me.clutchy.server.extensions.varInt

abstract class ClientPacket(val packetID: Int) {

    protected abstract fun getData(): ByteArray

    fun getPacketData(): ByteArray {
        val data = getData()
        val array: ByteArray = packetID.varInt() + data
        return array.varInt()
    }
}

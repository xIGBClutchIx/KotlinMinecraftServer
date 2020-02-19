package me.clutchy.server.packets

import kotlin.experimental.or

abstract class ClientPacket(val packetID: Int) {

    protected abstract fun getData(): ByteArray

    fun getPacketData(): ByteArray {
        val data = getData()
        var array: ByteArray = getVarInt(packetID)
        array += getVarInt(data.size)
        array += data
        return getVarInt(array.size) + array
    }

    private fun getVarInt(int: Int): ByteArray {
        var array = byteArrayOf()
        var value = int
        do {
            var temp: Byte = (value and 0b01111111).toByte()
            value = value ushr 7
            if (value != 0) {
                temp = (temp or 0b10000000.toByte())
            }
            array += temp
        } while (value != 0)
        return array
    }
}

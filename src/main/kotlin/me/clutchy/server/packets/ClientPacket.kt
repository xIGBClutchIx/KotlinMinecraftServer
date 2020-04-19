package me.clutchy.server.packets

import java.nio.charset.StandardCharsets

abstract class ClientPacket(val packetID: Int) {

    protected abstract fun getData(): ByteArray

    fun getPacketData(): ByteArray {
        val data = getData()
        val array: ByteArray = varInt(packetID) + data
        return varInt(array.size) + array
    }

    fun string(value: String): ByteArray {
        val valueArray = value.toByteArray(StandardCharsets.UTF_8)
        return varInt(valueArray.size) + valueArray
    }

    fun varInt(int: Int): ByteArray {
        var array = byteArrayOf()
        var value = int
        while (value and -128 != 0) {
            array += (value and 127 or 128).toByte()
            value = value ushr 7
        }
        array += value.toByte()
        return array
    }
}

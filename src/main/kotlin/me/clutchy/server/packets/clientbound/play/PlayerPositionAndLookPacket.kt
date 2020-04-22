package me.clutchy.server.packets.clientbound.play

import me.clutchy.server.extensions.byteArray
import me.clutchy.server.extensions.varInt
import me.clutchy.server.packets.ClientPacket

class PlayerPositionAndLookPacket: ClientPacket(0x36) {

    private var x: Double = 0.0
    private var y: Double = 100.0
    private var z: Double = 0.0
    private var yaw: Float = 0F
    private var pitch: Float = 0F
    private var relative: ArrayList<PositionFlag> = arrayListOf()
    private var teleportId: Int = 0

    override fun getData(): ByteArray {
        var array = x.byteArray()
        array += y.byteArray()
        array += z.byteArray()
        array += yaw.byteArray()
        array += pitch.byteArray()
        var flags = 0
        for (element in relative) {
            flags = flags or (1 shl element.ordinal)
        }
        array += byteArrayOf(flags.toByte())
        array += teleportId.varInt()
        return array
    }

    enum class PositionFlag {
        X, Y, Z, Y_ROT, X_ROT
    }
}

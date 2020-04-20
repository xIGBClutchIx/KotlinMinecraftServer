package me.clutchy.server.packets.clientbound.login

import me.clutchy.server.network.EncryptionManager
import me.clutchy.server.extensions.byteArray
import me.clutchy.server.extensions.varInt
import me.clutchy.server.packets.ClientPacket
import java.util.*

class EncryptionRequestPacket: ClientPacket(0x01) {

    private val randomData = ByteArray(4)

    override fun getData(): ByteArray {
        var array = "".byteArray()
        array += EncryptionManager.keyPair.public.encoded.varInt()
        Random().nextBytes(randomData)
        array += randomData.varInt()
        return array
    }
}

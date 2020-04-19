package me.clutchy.server.packets.client.login

import me.clutchy.server.EncryptionManager
import me.clutchy.server.packets.ClientPacket
import java.util.*

class EncryptionRequestPacket: ClientPacket(1) {

    private val randomData = ByteArray(4)

    override fun getData(): ByteArray {
        var array = string("")
        array += varInt(EncryptionManager.keyPair.public.encoded.size)
        array += EncryptionManager.keyPair.public.encoded
        Random().nextBytes(randomData)
        array += varInt(randomData.size)
        array += randomData
        return array
    }
}

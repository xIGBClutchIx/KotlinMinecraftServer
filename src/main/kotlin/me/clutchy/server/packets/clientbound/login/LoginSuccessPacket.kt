package me.clutchy.server.packets.clientbound.login

import me.clutchy.server.extensions.byteArray
import me.clutchy.server.packets.ClientPacket
import java.util.*

class LoginSuccessPacket(private val username: String): ClientPacket(0x02) {

    private val uuid = createUUID(username)

    private fun createUUID(name: String): UUID {
        return UUID.nameUUIDFromBytes("OfflinePlayer:$name".toByteArray(Charsets.UTF_8))
    }

    override fun getData(): ByteArray {
        var array = uuid.toString().byteArray(36)
        array += username.byteArray(5)
        return array
    }
}

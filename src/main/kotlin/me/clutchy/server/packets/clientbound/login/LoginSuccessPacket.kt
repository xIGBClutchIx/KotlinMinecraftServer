package me.clutchy.server.packets.clientbound.login

import me.clutchy.server.extensions.byteArray
import me.clutchy.server.packets.ClientPacket
import java.util.*

class LoginSuccessPacket(private val username: String): ClientPacket(0x02) {

    private val uuid = createUUID(username)

    private fun createUUID(name: String): UUID = UUID.nameUUIDFromBytes(name.toByteArray(Charsets.UTF_8))

    override fun getData(): ByteArray {
        // UUID - String
        var array = uuid.toString().byteArray(36)
        // Username - String
        array += username.byteArray(16)
        return array
    }
}

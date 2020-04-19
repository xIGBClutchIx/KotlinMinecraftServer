package me.clutchy.server.packets.client.login

import me.clutchy.server.packets.ClientPacket
import java.nio.charset.StandardCharsets
import java.util.*

class LoginPacket(private val username: String): ClientPacket(2) {

    private val uuid: UUID

    init {
        uuid = createUUID(username)
    }

    private fun createUUID(name: String): UUID {
        return UUID.nameUUIDFromBytes("OfflinePlayer:$name".toByteArray(StandardCharsets.UTF_8))
    }

    override fun getData(): ByteArray {
        var array = string(uuid.toString())
        array += string(username)
        return array
    }
}

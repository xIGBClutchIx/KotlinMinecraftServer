package me.clutchy.server.packets.clientbound.play

import me.clutchy.server.extensions.byteArray
import me.clutchy.server.packets.ClientPacket

class ChatMessagePacket: ClientPacket(0x0F) {

    private var chat: String = "{\"text\":\"Welcome to the fake server\",\"color\":\"dark_aqua\"}"
    private var position: Byte = 0

    override fun getData(): ByteArray {
        var array = chat.byteArray(32767)
        array += byteArrayOf(position)
        return array
    }
}

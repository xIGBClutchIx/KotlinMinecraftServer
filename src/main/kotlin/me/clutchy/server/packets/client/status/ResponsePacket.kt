package me.clutchy.server.packets.client.status

import com.google.gson.Gson
import me.clutchy.server.extensions.byteArray
import me.clutchy.server.extensions.string
import me.clutchy.server.packets.ClientPacket
import me.clutchy.server.packets.client.status.json.ResponseJson

class ResponsePacket: ClientPacket(0) {

    override fun getData(): ByteArray {
        return Gson().toJson(ResponseJson()).byteArray()
    }
}

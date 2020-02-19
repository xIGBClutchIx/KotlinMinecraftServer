package me.clutchy.server.packets.client

import com.google.gson.Gson
import me.clutchy.server.packets.ClientPacket
import me.clutchy.server.packets.client.json.ResponseJson

class ResponsePacket: ClientPacket(0) {

    override fun getData(): ByteArray {
        return Gson().toJson(ResponseJson()).toByteArray(Charsets.UTF_8)
    }
}

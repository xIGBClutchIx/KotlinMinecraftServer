package me.clutchy.server.packets.clientbound.status

import com.beust.klaxon.Klaxon
import me.clutchy.server.extensions.byteArray
import me.clutchy.server.packets.ClientPacket
import me.clutchy.server.packets.clientbound.status.json.ResponseJson

class ResponsePacket(val protocolVersion: Int): ClientPacket(0x00) {

    override fun getData(): ByteArray {
        return Klaxon().toJsonString(ResponseJson(protocolVersion)).byteArray()
    }
}

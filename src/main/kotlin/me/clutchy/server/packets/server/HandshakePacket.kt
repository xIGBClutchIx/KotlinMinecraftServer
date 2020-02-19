package me.clutchy.server.packets.server

import me.clutchy.server.SocketConnection
import me.clutchy.server.extensions.short
import me.clutchy.server.extensions.string
import me.clutchy.server.extensions.varInt
import me.clutchy.server.packets.ServerPacket
import me.clutchy.server.packets.client.ResponsePacket
import java.io.DataInputStream

class HandshakePacket(data: DataInputStream, connection: SocketConnection): ServerPacket(data, connection) {

    var protocolVersion: Int = 0
        private set

    var serverAddress: String = ""
        private set

    var serverPort: Short = 0
        private set

    var state: HandshakeState = HandshakeState.REQUEST
        private set

    init {
        protocolVersion = data.varInt()
        serverAddress = data.string()
        serverPort = data.short()
        state = HandshakeState.values()[data.varInt()]
        if (connection !in stateHandler) {
            // Login happens here? - Because the og state is gone because pong closes it and this is the first one for login.
            if (state == HandshakeState.LOGIN) {
            }
        } else {
            // Check to make sure the last state was set. We should have no state/info here because fuck you we just want your response now
            if (stateHandler[connection] == HandshakeState.STATUS && state == HandshakeState.REQUEST) {
                connection.send(ResponsePacket())
            }
        }
        stateHandler[connection] = state
    }

    enum class HandshakeState {
        REQUEST,
        STATUS,
        LOGIN
    }

    companion object {
        val stateHandler = hashMapOf<SocketConnection, HandshakeState>()
    }
}

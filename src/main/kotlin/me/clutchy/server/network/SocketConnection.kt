package me.clutchy.server.network

import me.clutchy.server.extensions.print
import me.clutchy.server.extensions.readPacket
import me.clutchy.server.extensions.toHex
import me.clutchy.server.extensions.varInt
import me.clutchy.server.network.Server
import me.clutchy.server.packets.ClientPacket
import me.clutchy.server.packets.ServerPacketHandler
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket


class SocketConnection(private val socket: Socket): Runnable {

    var address: String = ""

    override fun run() {
        address = socket.localAddress.hostAddress
        "(${address}) -> Connected".print()
        while(true) {
            val data = DataInputStream(socket.getInputStream()).readPacket() ?: break
            if (data.available() <= 0) {
                return
            }
            val packetID = data.varInt()
            ServerPacketHandler.managePacket(packetID, data, this)
        }
        "(${address}) -> Disconnected".print()
        println()
        ServerPacketHandler.stateHandler.remove(this)
        Server.removeConnection(socket)

    }

    fun send(packet: ClientPacket) {
        val packetName = packet::class.simpleName.toString().removeSuffix("Packet").replace(Regex("(.)([A-Z])"), "$1 $2")
        "(${address}) <- ${packet.packetID.toHex()} [$packetName]".print()
        val outputStream = DataOutputStream(socket.getOutputStream())
        outputStream.write(packet.getPacketData())
        outputStream.flush()
    }
}

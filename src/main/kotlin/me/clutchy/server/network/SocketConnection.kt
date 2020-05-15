package me.clutchy.server.network

import com.github.ajalt.mordant.TermColors
import me.clutchy.server.extensions.readPacket
import me.clutchy.server.extensions.toHex
import me.clutchy.server.extensions.varInt
import me.clutchy.server.packets.ClientPacket
import me.clutchy.server.packets.ServerPacketHandler
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.net.Socket
import java.security.Key
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.crypto.Cipher

class SocketConnection(private val socket: Socket): Runnable {

    var address: String = ""
        private set

    var protocolVersion: Int = 0
    var username: String = ""

    override fun run() {
        address = socket.inetAddress.hostAddress
        printSide(PrintSide.CLIENT_TO_SERVER, t.green("Connected"))
        while(true) {
            if (socket.isClosed || !socket.isConnected || socket.isInputShutdown) break
            try {
                var data: InputStream = DataInputStream(socket.getInputStream()).readPacket() ?: break
                if (data.available() <= 0) {
                    return
                }
                data = DataInputStream(ByteArrayInputStream(data.readAllBytes()))
                val packetID = data.varInt()
                ServerPacketHandler.managePacket(packetID, data, this)
            } catch (e: Exception) {
                break
            }
        }
        printSide(PrintSide.CLIENT_TO_SERVER, t.red("Disconnected"))
        println()
        ServerPacketHandler.stateHandler.remove(this)
        Server.removeConnection(socket)

    }

    fun send(packet: ClientPacket) {
        val packetName = packet::class.simpleName.toString().removeSuffix("Packet").replace(Regex("(.)([A-Z])"), "$1 $2")
        printSide(PrintSide.SERVER_TO_CLIENT, t.brightBlue("(${packet.packetID.toHex()}) [$packetName]"))
        val outputStream = DataOutputStream(socket.getOutputStream())
        outputStream.write(packet.getPacketData())
        outputStream.flush()
    }

    fun printSide(side: PrintSide, message: String) {
        var printMessage = t.gray("[") + t.cyan(LocalDateTime.now().format(formatter)) + t.gray("] ") +
                t.gray("(") + t.reset(address) + t.gray(")")
        printMessage += when (side) {
            PrintSide.SERVER_TO_CLIENT -> {
                t.reset(" <- ")
            }
            PrintSide.CLIENT_TO_SERVER -> {
                t.reset(" -> ")
            }
            PrintSide.SIDE -> {
                t.reset(" =  ")
            }
        }
        printMessage += message
        println(printMessage)
    }

    fun close() {
        socket.close()
    }

    enum class PrintSide {
        SERVER_TO_CLIENT,
        CLIENT_TO_SERVER,
        SIDE
    }

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")
        val t = TermColors()
    }
}

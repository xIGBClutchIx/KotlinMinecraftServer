package me.clutchy.server.extensions

import me.clutchy.server.network.Server
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*
import javax.imageio.ImageIO

fun String.resourceToBase64(): String {
    val resource = Server::class.java.classLoader.getResource(this) ?: return ""
    val stream = ByteArrayOutputStream()
    return try {
        ImageIO.write(ImageIO.read(resource), "png", stream)
        "data:image/png;base64,${Base64.getEncoder().encodeToString(stream.toByteArray())}"
    } catch (e: IOException) {
        ""
    }
}

fun String.byteArray(maxSize: Int): ByteArray {
    var valueArray = this.toByteArray(Charsets.UTF_8)
    if (valueArray.size > maxSize) {
        valueArray = this.substring(0, maxSize - 1).toByteArray(Charsets.UTF_8)
    }
    return valueArray.size.varInt() + valueArray
}

fun String.byteArray(): ByteArray = this.byteArray(32766)

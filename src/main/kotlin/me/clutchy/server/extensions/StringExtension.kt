package me.clutchy.server.extensions

import me.clutchy.server.Server
import java.io.ByteArrayOutputStream
import java.io.IOException
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

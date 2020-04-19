package me.clutchy.server.packets.client.status.json

import me.clutchy.server.extensions.resourceToBase64

data class ResponseJson(val version: VersionJson = VersionJson(), val players: PlayersJson = PlayersJson(),
                        val description: DescriptionJson = DescriptionJson(),
                        val favicon: String = "default-server-icon.png".resourceToBase64()) {

    data class VersionJson(val name: String = "1.15.2", val protocol: Int = 578)

    data class PlayersJson(val max: Int = 64, val online: Int = 1, val sample: Array<SamplePlayerJson> = arrayOf(SamplePlayerJson())) {
        data class SamplePlayerJson(val name: String = "Clutch", val id: String = "2a8e267f-88d7-4175-8825-00e81a680076")
    }

    data class DescriptionJson(val text: String = "A Minecraft Server")
}

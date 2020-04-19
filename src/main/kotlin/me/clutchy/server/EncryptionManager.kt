package me.clutchy.server

import java.security.KeyPair
import java.security.KeyPairGenerator

class EncryptionManager {

    companion object {
        val keyPair: KeyPair

        init {
            val keyGen = KeyPairGenerator.getInstance("RSA")
            keyGen.initialize(1024)
            keyPair = keyGen.generateKeyPair()
        }
    }
}

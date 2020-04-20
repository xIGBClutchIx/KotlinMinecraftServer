package me.clutchy.server

import kotlin.random.Random

class EIDManager {

    companion object {
        private val eids: ArrayList<Int> = arrayListOf()

        fun randomEntityID(): Int {
            var id = Random.nextInt(4)
            while (eids.contains(id)) {
                id = Random.nextInt(4)
            }
            eids.add(id)
            return id
        }
    }
}

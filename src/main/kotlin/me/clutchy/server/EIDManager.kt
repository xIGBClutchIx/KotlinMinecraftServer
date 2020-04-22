package me.clutchy.server

import java.util.concurrent.atomic.AtomicInteger

class EIDManager {

    companion object {
        private val ENTITY_COUNTER = AtomicInteger()

        fun getNextID(): Int {
            return ENTITY_COUNTER.incrementAndGet()
        }
    }
}

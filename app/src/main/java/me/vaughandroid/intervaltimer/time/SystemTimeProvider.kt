package me.vaughandroid.intervaltimer.time

import kotlin.concurrent.thread

class SystemTimeProvider(
    private val tickMillis: Long = 100
) : TimeProvider {

    override val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    override val tickSubscribers = mutableSetOf<(Long) -> Unit>()

    init {
        thread(start = true, isDaemon = true) {
            while (true) {
                Thread.sleep(tickMillis)
                val millis = currentTimeMillis
                tickSubscribers.forEach { it.invoke(millis) }
            }
        }
    }

}

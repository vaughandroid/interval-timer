package me.vaughandroid.intervaltimer.time

class TestTimeProvider(
    startTimeMillis: Long = 42L
) : TimeProvider {

    override var currentTimeMillis: Long = startTimeMillis
        private set

    override val tickSubscribers = mutableListOf<(Long) -> Unit>()

    init {
        currentTimeMillis = startTimeMillis
    }

    fun advanceTime(duration: Duration) {
        currentTimeMillis += duration.millis
        tickSubscribers.forEach { it.invoke(currentTimeMillis) }
    }

}
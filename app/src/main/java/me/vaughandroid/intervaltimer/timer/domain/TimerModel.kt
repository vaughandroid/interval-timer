package me.vaughandroid.intervaltimer.timer.domain

import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.TimeProvider

class TimerModel(
    configurationStore: ConfigurationStore,
    private val timeProvider: TimeProvider
) {

    private val configuration = configurationStore.getConfiguration()
    val totalSets: Int = configuration.sets

    var currentSegmentTimeRemaining: Duration
    var currentState: TimerState = TimerState.READY

    var currentSegmentTimeChangedListener: ((Duration) -> Unit)? = null

    private var tickSubscriber = TickSubscriber()

    init {
        currentSegmentTimeRemaining = configuration.workTime
    }

    fun start() {
        tickSubscriber.lastTimeMillis = timeProvider.currentTimeMillis
        timeProvider.tickSubscribers += tickSubscriber
        currentState = TimerState.WORK_RUNNING
    }

    fun pause() {
        timeProvider.tickSubscribers -= tickSubscriber
        currentState = TimerState.WORK_PAUSED
    }

    private fun advanceTime(elapsedMillis: Long) {
        val newRemainingMillis = currentSegmentTimeRemaining.millis - elapsedMillis.toInt()

        if (newRemainingMillis > 0) {
            currentSegmentTimeRemaining = Duration(newRemainingMillis)
        } else {
            currentState = TimerState.REST_RUNNING
            val newSegmentMillis = configuration.restTime.millis + newRemainingMillis
            currentSegmentTimeRemaining = Duration(newSegmentMillis)
        }

        currentSegmentTimeChangedListener?.invoke(currentSegmentTimeRemaining)
    }

    private inner class TickSubscriber: (Long) -> Unit {
        var lastTimeMillis = timeProvider.currentTimeMillis

        override fun invoke(newTimeMillis: Long) {
            val elapsedMillis = newTimeMillis - lastTimeMillis
            lastTimeMillis = newTimeMillis
            advanceTime(elapsedMillis)
        }

    }
}

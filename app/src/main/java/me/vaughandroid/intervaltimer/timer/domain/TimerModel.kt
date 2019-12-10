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
        private set
    var currentState: TimerState = TimerState.READY
        private set

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

    fun enterState(newState: TimerState, durationOffsetMillis: Int = 0) {
        currentState = newState

        val segmentMillis = when(newState) {
            TimerState.READY, TimerState.WORK_RUNNING, TimerState.WORK_PAUSED -> configuration.workTime.millis
            TimerState.REST_RUNNING, TimerState.REST_PAUSED -> configuration.restTime.millis
        }
        currentSegmentTimeRemaining = Duration(segmentMillis + durationOffsetMillis)

        when(newState) {
            TimerState.WORK_RUNNING, TimerState.REST_RUNNING ->
                timeProvider.tickSubscribers += tickSubscriber
            else ->
                timeProvider.tickSubscribers -= tickSubscriber
        }
    }

    private fun advanceTime(elapsedMillis: Long) {
        val newRemainingMillis = currentSegmentTimeRemaining.millis - elapsedMillis.toInt()

        if (newRemainingMillis > 0) {
            currentSegmentTimeRemaining = Duration(newRemainingMillis)
        } else {
            enterState(TimerState.REST_RUNNING, newRemainingMillis)
        }

        currentSegmentTimeChangedListener?.invoke(currentSegmentTimeRemaining)
    }

    private inner class TickSubscriber : (Long) -> Unit {
        var lastTimeMillis = timeProvider.currentTimeMillis

        override fun invoke(newTimeMillis: Long) {
            val elapsedMillis = newTimeMillis - lastTimeMillis
            lastTimeMillis = newTimeMillis
            advanceTime(elapsedMillis)
        }

    }
}

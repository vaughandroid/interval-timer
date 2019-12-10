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

    val currentSegmentType: SegmentType
        get() = currentState.segmentType
    val timerRunningState: TimerRunningState
        get() = currentState.timerRunningState

    var currentSegmentTimeChangedListener: ((Duration) -> Unit)? = null

    private var tickSubscriber = TickSubscriber()

    init {
        currentSegmentTimeRemaining = configuration.workTime
    }

    fun start() {
        tickSubscriber.lastTimeMillis = timeProvider.currentTimeMillis
        timeProvider.tickSubscribers += tickSubscriber
        currentState = when (currentState.segmentType) {
            SegmentType.READY, SegmentType.WORK -> TimerState.WORK_RUNNING
            SegmentType.REST -> TimerState.REST_RUNNING
        }
    }

    fun pause() {
        timeProvider.tickSubscribers -= tickSubscriber
        currentState = when (currentState.segmentType) {
            SegmentType.READY, SegmentType.WORK -> TimerState.WORK_PAUSED
            SegmentType.REST -> TimerState.REST_PAUSED
        }
    }

    fun enterState(newState: TimerState, durationAdjustment: Duration = Duration.ZERO) {
        currentState = newState

        val segmentMillis = when (newState.segmentType) {
            SegmentType.READY, SegmentType.WORK -> configuration.workTime
            SegmentType.REST -> configuration.restTime
        }
        currentSegmentTimeRemaining = segmentMillis + durationAdjustment

        when (newState) {
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
            val nextState = when (currentState) {
                TimerState.READY -> TimerState.WORK_RUNNING
                TimerState.WORK_RUNNING -> TimerState.REST_RUNNING
                TimerState.WORK_PAUSED -> TimerState.REST_PAUSED
                TimerState.REST_RUNNING -> TimerState.WORK_RUNNING
                TimerState.REST_PAUSED -> TimerState.WORK_PAUSED
            }
            enterState(nextState, Duration(newRemainingMillis))
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

package me.vaughandroid.intervaltimer.timer.domain

import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.TimeProvider
import kotlin.properties.Delegates

class TimerModel(
    configurationStore: ConfigurationStore,
    private val timeProvider: TimeProvider
) {

    private val configuration = configurationStore.getConfiguration()

    val totalSets: Int = configuration.sets

    var currentSegmentTimeRemaining: Duration
        private set

    var currentTimerState: TimerState = TimerState.READY
        private set

    var runningState: RunningState
            by Delegates.observable(RunningState.PAUSED) { _, oldValue, newValue ->
                if (oldValue != newValue) {
                    tickSubscriber.lastTimeMillis = timeProvider.currentTimeMillis
                }
                when (newValue) {
                    RunningState.RUNNING -> timeProvider.tickSubscribers += tickSubscriber
                    RunningState.PAUSED -> timeProvider.tickSubscribers -= tickSubscriber
                }
            }
        private set

    var currentSegmentTimeChangedListener: ((Duration) -> Unit)? = null

    private var tickSubscriber = TickSubscriber()

    init {
        currentSegmentTimeRemaining = configuration.workTime
    }

    fun start() {
        runningState = RunningState.RUNNING
    }

    fun pause() {
        runningState = RunningState.PAUSED
    }

    // TODO: This is only used for setting test starting conditions. Replace with a constructor param.
    fun enterState(
        newTimerState: TimerState,
        newRunningState: RunningState,
        durationAdjustment: Duration = Duration.ZERO
    ) {
        enterSegment(newTimerState, durationAdjustment)

        runningState = newRunningState
    }

    private fun enterSegment(
        newTimerState: TimerState,
        durationAdjustment: Duration = Duration.ZERO
    ) {
        currentTimerState = newTimerState

        val segmentMillis = when (newTimerState) {
            TimerState.READY -> Duration.ZERO
            TimerState.WORK -> configuration.workTime
            TimerState.REST -> configuration.restTime
        }
        currentSegmentTimeRemaining = segmentMillis + durationAdjustment
    }

    private fun advanceTime(elapsedMillis: Long) {
        val newRemainingMillis = currentSegmentTimeRemaining.millis - elapsedMillis.toInt()

        if (newRemainingMillis > 0) {
            currentSegmentTimeRemaining = Duration(newRemainingMillis)
        } else {
            val nextSegment = when (currentTimerState) {
                TimerState.READY, TimerState.REST -> TimerState.WORK
                else -> TimerState.REST
            }
            enterSegment(nextSegment, Duration(newRemainingMillis))
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

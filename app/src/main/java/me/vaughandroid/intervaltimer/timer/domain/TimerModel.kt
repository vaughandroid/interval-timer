package me.vaughandroid.intervaltimer.timer.domain

import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.TimeProvider

class TimerModel(
    configurationStore: ConfigurationStore,
    private val timeProvider: TimeProvider
) {

    val totalSets: Int

    var currentWorkTime: Duration
    var currentState: TimerState = TimerState.READY

    var workTimeChangedListener: ((Duration) -> Unit)? = null

    private var tickSubscriber = TickSubscriber()

    init {
        val (sets, workTime, _) = configurationStore.getConfiguration()
        totalSets = sets
        currentWorkTime = workTime
    }

    fun start() {
        tickSubscriber.currentTimeMillis = timeProvider.currentTimeMillis
        timeProvider.tickSubscribers += tickSubscriber
        currentState = TimerState.WORK_RUNNING
    }

    fun pause() {
        timeProvider.tickSubscribers -= tickSubscriber
        currentState = TimerState.WORK_PAUSED
    }

    private inner class TickSubscriber: (Long) -> Unit {
        var currentTimeMillis = timeProvider.currentTimeMillis

        override fun invoke(newTimeMillis: Long) {
            val elapsedMillis = newTimeMillis - currentTimeMillis
            currentTimeMillis = newTimeMillis
            currentWorkTime = Duration(currentWorkTime.millis - elapsedMillis.toInt())
            workTimeChangedListener?.invoke(currentWorkTime)
        }

    }
}

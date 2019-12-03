package me.vaughandroid.intervaltimer.timer.domain

import android.util.Log
import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.TimeProvider

class TimerModel(
    configurationStore: ConfigurationStore,
    private val timeProvider: TimeProvider
) {

    val currentSets: Int
    var currentWorkTime: Duration
    val currentRestTime: Duration

    var workTimeChangedListener: ((Duration) -> Unit)? = null

    init {
        val (sets, workTime, restTime) = configurationStore.getConfiguration()
        currentSets = sets
        currentWorkTime = workTime
        currentRestTime = restTime
    }

    fun start() {
        var currentTimeMillis = timeProvider.currentTimeMillis

        val runnable = {
            while(true) {
                Thread.sleep(100)
                val newTimeMillis = timeProvider.currentTimeMillis
                val elapsedMillis = newTimeMillis - currentTimeMillis
                Log.d("XXX", "$elapsedMillis")
                currentTimeMillis = newTimeMillis
                currentWorkTime = Duration(currentWorkTime.millis - elapsedMillis.toInt())
                workTimeChangedListener?.invoke(currentWorkTime)
            }
        }
        Thread(runnable).start()
    }

}
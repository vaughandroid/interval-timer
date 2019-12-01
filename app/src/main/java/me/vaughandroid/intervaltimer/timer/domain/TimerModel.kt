package me.vaughandroid.intervaltimer.timer.domain

import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.time.Duration

class TimerModel(configurationStore: ConfigurationStore) {

    var currentSets: Int
    var currentWorkTime: Duration
    var currentRestTime: Duration

    init {
        val (sets, workTime, restTime) = configurationStore.getConfiguration()
        currentSets = sets
        currentWorkTime = workTime
        currentRestTime = restTime
    }

}
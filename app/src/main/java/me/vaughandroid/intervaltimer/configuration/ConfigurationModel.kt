package me.vaughandroid.intervaltimer.configuration

import me.vaughandroid.intervaltimer.time.seconds
import me.vaughandroid.intervaltimer.time.coerceAtLeast
import me.vaughandroid.intervaltimer.time.coerceAtMost
import me.vaughandroid.intervaltimer.time.hours

class ConfigurationModel(initialConfiguration: Configuration) {

    var currentConfiguration: Configuration = initialConfiguration
        private set

    fun decrementSets() {
        val newSets = (currentConfiguration.sets - 1).coerceAtLeast(1)
        currentConfiguration = currentConfiguration.copy(sets = newSets)
    }

    fun incrementSets() {
        val newSets = (currentConfiguration.sets + 1).coerceAtMost(99)
        currentConfiguration = currentConfiguration.copy(sets = newSets)
    }

    fun incrementWorkTime() {
        val newWorkTime = (currentConfiguration.workTime + 1.seconds).coerceAtMost(1.hours.toSeconds)
        currentConfiguration = currentConfiguration.copy(workTime = newWorkTime)
    }

    fun decrementWorkTime() {
        val newWorkTime = (currentConfiguration.workTime - 1.seconds).coerceAtLeast(1.seconds)
        currentConfiguration = currentConfiguration.copy(workTime = newWorkTime)
    }

}

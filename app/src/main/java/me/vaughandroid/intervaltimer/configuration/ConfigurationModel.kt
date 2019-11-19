package me.vaughandroid.intervaltimer.configuration

import me.vaughandroid.intervaltimer.time.coerceAtLeast
import me.vaughandroid.intervaltimer.time.coerceAtMost
import me.vaughandroid.intervaltimer.time.hours
import me.vaughandroid.intervaltimer.time.seconds
import kotlin.properties.Delegates

class ConfigurationModel(
    initialConfiguration: Configuration = Configuration()
) {

    var currentConfiguration: Configuration
            by Delegates.observable(initialConfiguration) { _, _, newValue ->
                onConfigurationChanged?.invoke(newValue)
            }
        private set

    var onConfigurationChanged: ((Configuration) -> Unit)? = null

    fun decrementSets() {
        val newSets = (currentConfiguration.sets - 1).coerceAtLeast(1)
        currentConfiguration = currentConfiguration.copy(sets = newSets)
    }

    fun incrementSets() {
        val newSets = (currentConfiguration.sets + 1).coerceAtMost(99)
        currentConfiguration = currentConfiguration.copy(sets = newSets)
    }

    fun incrementWorkTime() {
        val newWorkTime =
            (currentConfiguration.workTime + 1.seconds).coerceAtMost(1.hours.toSeconds)
        currentConfiguration = currentConfiguration.copy(workTime = newWorkTime)
    }

    fun decrementWorkTime() {
        val newWorkTime = (currentConfiguration.workTime - 1.seconds).coerceAtLeast(1.seconds)
        currentConfiguration = currentConfiguration.copy(workTime = newWorkTime)
    }

    fun incrementRestTime() {
        val newRestTime =
            (currentConfiguration.restTime + 1.seconds).coerceAtMost(1.hours.toSeconds)
        currentConfiguration = currentConfiguration.copy(restTime = newRestTime)
    }

    fun decrementRestTime() {
        val newRestTime = (currentConfiguration.restTime - 1.seconds).coerceAtLeast(1.seconds)
        currentConfiguration = currentConfiguration.copy(restTime = newRestTime)
    }

}

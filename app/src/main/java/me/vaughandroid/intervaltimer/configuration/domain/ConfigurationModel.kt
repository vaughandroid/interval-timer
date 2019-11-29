package me.vaughandroid.intervaltimer.configuration.domain

import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.time.hours
import me.vaughandroid.intervaltimer.time.seconds

class ConfigurationModel(
    private val configurationStore: ConfigurationStore
) {

    var currentConfiguration: Configuration = configurationStore.getConfiguration()
        private set

    var configurationChangedListener: ((Configuration) -> Unit)? = null

    fun updateConfiguration(newConfiguration: Configuration) {
        val constrainedConfiguration = applyConstraints(newConfiguration)
        currentConfiguration = constrainedConfiguration
        configurationStore.putConfiguration(constrainedConfiguration)
        configurationChangedListener?.invoke(constrainedConfiguration)
    }

    private fun applyConstraints(configuration: Configuration) =
        Configuration(
            sets = configuration.sets.coerceIn(1, 99),
            workTime = configuration.workTime.coerceIn(1.seconds, 1.hours),
            restTime = configuration.restTime.coerceIn(1.seconds, 1.hours)
        )

}

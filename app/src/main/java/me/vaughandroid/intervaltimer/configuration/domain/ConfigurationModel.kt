package me.vaughandroid.intervaltimer.configuration.domain

import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.time.hours
import me.vaughandroid.intervaltimer.time.seconds

class ConfigurationModel constructor(
    initialConfiguration: Configuration = Configuration()
) {
    constructor(configurationStore: ConfigurationStore) : this(configurationStore.getConfiguration())

    var currentConfiguration: Configuration = initialConfiguration
        private set

    var configurationChangedListener: ((Configuration) -> Unit)? = null

    fun updateConfiguration(newConfiguration: Configuration) {
        val constrainedConfiguration = applyConstraints(newConfiguration)
        currentConfiguration = constrainedConfiguration
        configurationChangedListener?.invoke(constrainedConfiguration)
    }

    private fun applyConstraints(configuration: Configuration) =
        Configuration(
            sets = configuration.sets.coerceIn(1, 99),
            workTime = configuration.workTime.coerceIn(1.seconds, 1.hours),
            restTime = configuration.restTime.coerceIn(1.seconds, 1.hours)
        )

}

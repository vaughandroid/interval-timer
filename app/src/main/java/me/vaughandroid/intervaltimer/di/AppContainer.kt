package me.vaughandroid.intervaltimer.di

import android.app.Application
import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.configuration.data.SharedPreferencesConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel

class AppContainer(
    private val configurationStore: ConfigurationStore
) {

    constructor(application: Application) : this(SharedPreferencesConfigurationStore(application))

    fun createConfigurationModel(): ConfigurationModel =
        ConfigurationModel(configurationStore)

}
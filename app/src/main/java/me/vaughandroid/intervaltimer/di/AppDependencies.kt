package me.vaughandroid.intervaltimer.di

import android.app.Application
import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.configuration.data.SharedPreferencesConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.timer.domain.TimerModel

class AppDependencies(
    private val configurationStore: ConfigurationStore
) {

    constructor(application: Application) : this(SharedPreferencesConfigurationStore(application))

    fun createConfigurationModel(): ConfigurationModel =
        ConfigurationModel(configurationStore)

    fun createTimerModel(): TimerModel =
        TimerModel(configurationStore)

}
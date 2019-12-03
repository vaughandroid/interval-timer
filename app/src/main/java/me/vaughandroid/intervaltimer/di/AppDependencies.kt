package me.vaughandroid.intervaltimer.di

import android.app.Application
import me.vaughandroid.intervaltimer.configuration.data.ConfigurationStore
import me.vaughandroid.intervaltimer.configuration.data.SharedPreferencesConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.time.SystemTimeProvider
import me.vaughandroid.intervaltimer.time.TimeProvider
import me.vaughandroid.intervaltimer.timer.domain.TimerModel

class AppDependencies(
    private val configurationStore: ConfigurationStore,
    private val timeProvider: TimeProvider = SystemTimeProvider
) {

    constructor(application: Application) : this(
        SharedPreferencesConfigurationStore(application)
    )

    fun createConfigurationModel(): ConfigurationModel =
        ConfigurationModel(configurationStore)

    fun createTimerModel(): TimerModel =
        TimerModel(
            configurationStore,
            timeProvider
        )

}
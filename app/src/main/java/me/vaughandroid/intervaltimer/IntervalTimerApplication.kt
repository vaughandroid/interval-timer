package me.vaughandroid.intervaltimer

import android.app.Application
import me.vaughandroid.intervaltimer.configuration.data.SharedPreferencesConfigurationStore
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.di.ViewModelFactory

@Suppress("unused")
class IntervalTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initViewModelFactory()
    }

    private fun initViewModelFactory() {
        val configurationStore = SharedPreferencesConfigurationStore(this)
        ViewModelFactory.configurationModel = ConfigurationModel(configurationStore)
    }

}
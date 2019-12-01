package me.vaughandroid.intervaltimer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.vaughandroid.intervaltimer.configuration.view.ConfigurationViewModel
import me.vaughandroid.intervaltimer.timer.view.TimerViewModel

class ViewModelFactory(
    private val appDependencies: AppDependencies
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ConfigurationViewModel::class.java) -> {
                ConfigurationViewModel(appDependencies.createConfigurationModel()) as T
            }
            modelClass.isAssignableFrom(TimerViewModel::class.java) -> {
                TimerViewModel(appDependencies.createTimerModel()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel type: ${modelClass.simpleName}")
        }
    }

}

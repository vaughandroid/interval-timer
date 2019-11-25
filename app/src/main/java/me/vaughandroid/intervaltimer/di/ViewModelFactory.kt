package me.vaughandroid.intervaltimer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.configuration.view.ConfigurationViewModel
import java.lang.IllegalArgumentException

object ViewModelFactory : ViewModelProvider.Factory {

    var configurationModel = ConfigurationModel()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ConfigurationViewModel::class.java) -> {
                ConfigurationViewModel(configurationModel) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel type: ${modelClass.simpleName}")
        }
    }

}

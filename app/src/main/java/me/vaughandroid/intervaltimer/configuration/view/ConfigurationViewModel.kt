package me.vaughandroid.intervaltimer.configuration.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.time.DurationFormatter
import me.vaughandroid.intervaltimer.time.seconds

class ConfigurationViewModel(
    private val configurationModel: ConfigurationModel
) : ViewModel() {

    private val mutableViewDataLiveData = MutableLiveData<ConfigurationViewData>()

    val viewDataLiveData: LiveData<ConfigurationViewData> = mutableViewDataLiveData

    private val currentConfiguration: Configuration
            get() = configurationModel.currentConfiguration

    init {
        configurationModel.configurationChangedListener = { configuration ->
            val viewData = configuration.toViewData()
            mutableViewDataLiveData.postValue(viewData)
        }

        val initialViewData = currentConfiguration.toViewData()
        mutableViewDataLiveData.postValue(initialViewData)
    }

    fun incrementSets() {
        val newConfiguration = currentConfiguration.copy(sets = currentConfiguration.sets + 1)
        configurationModel.updateConfiguration(newConfiguration)
    }

    fun decrementSets() {
        val newConfiguration = currentConfiguration.copy(sets = currentConfiguration.sets - 1)
        configurationModel.updateConfiguration(newConfiguration)
    }

    fun incrementWorkTime() {
        val newConfiguration =
            currentConfiguration.copy(workTime = currentConfiguration.workTime + 1.seconds)
        configurationModel.updateConfiguration(newConfiguration)
    }

    fun decrementWorkTime() {
        val newConfiguration =
            currentConfiguration.copy(workTime = currentConfiguration.workTime - 1.seconds)
        configurationModel.updateConfiguration(newConfiguration)
    }

    fun incrementRestTime() {
        val newConfiguration =
            currentConfiguration.copy(restTime = currentConfiguration.restTime + 1.seconds)
        configurationModel.updateConfiguration(newConfiguration)
    }

    fun decrementRestTime() {
        val newConfiguration =
            currentConfiguration.copy(restTime = currentConfiguration.restTime - 1.seconds)
        configurationModel.updateConfiguration(newConfiguration)
    }

}

private fun Configuration.toViewData(): ConfigurationViewData =
    ConfigurationViewData(
        setsText = sets.toString(),
        workTimeText = DurationFormatter.toDisplayString(workTime),
        restTimeText = DurationFormatter.toDisplayString(restTime)
    )
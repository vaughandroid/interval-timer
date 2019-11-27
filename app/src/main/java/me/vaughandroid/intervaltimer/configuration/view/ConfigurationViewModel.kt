package me.vaughandroid.intervaltimer.configuration.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.time.DurationFormatter

class ConfigurationViewModel(
    private val configurationModel: ConfigurationModel
) : ViewModel() {

    private val mutableViewDataLiveData = MutableLiveData<ConfigurationViewData>()

    val viewDataLiveData: LiveData<ConfigurationViewData> = mutableViewDataLiveData

    init {
        configurationModel.onConfigurationChanged = { configuration ->
            val viewData = configuration.toViewData()
            mutableViewDataLiveData.postValue(viewData)
        }

        val initialViewData = configurationModel.currentConfiguration.toViewData()
        mutableViewDataLiveData.postValue(initialViewData)
    }

    fun incrementSets() {
        configurationModel.incrementSets()
    }

    fun decrementSets() {
        configurationModel.decrementSets()
    }

    fun incrementWorkTime() {
        configurationModel.incrementWorkTime()
    }

    fun decrementWorkTime() {
        configurationModel.decrementWorkTime()
    }

    fun incrementRestTime() {
        configurationModel.incrementRestTime()
    }

    fun decrementRestTime() {
        configurationModel.decrementRestTime()
    }

}

private fun Configuration.toViewData(): ConfigurationViewData =
    ConfigurationViewData(
        setsText = sets.toString(),
        workTimeText = DurationFormatter.toDisplayString(workTime),
        restTimeText = DurationFormatter.toDisplayString(restTime)
    )
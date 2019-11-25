package me.vaughandroid.intervaltimer.configuration.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel

class ConfigurationViewModel(
    private val configurationModel: ConfigurationModel
) : ViewModel() {

    private val mutableViewDataLiveData = MutableLiveData<ConfigurationViewData>()

    val viewDataLiveData : LiveData<ConfigurationViewData> = mutableViewDataLiveData

    init {
        configurationModel.onConfigurationChanged = { configuration ->
            val viewData = ConfigurationPresenter.transform(configuration)
            mutableViewDataLiveData.postValue(viewData)
        }

        val initialViewData = ConfigurationPresenter.transform(configurationModel.currentConfiguration)
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
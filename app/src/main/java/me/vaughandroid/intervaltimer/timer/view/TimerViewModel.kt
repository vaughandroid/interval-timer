package me.vaughandroid.intervaltimer.timer.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private val mutableLiveData =
        MutableLiveData<TimerViewData>()

    val viewDataLiveData: LiveData<TimerViewData> = mutableLiveData

    init {
        mutableLiveData.value = TimerViewData("", "", "")
    }

}
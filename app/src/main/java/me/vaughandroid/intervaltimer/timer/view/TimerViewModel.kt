package me.vaughandroid.intervaltimer.timer.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.vaughandroid.intervaltimer.time.DurationFormatter
import me.vaughandroid.intervaltimer.timer.domain.TimerModel

class TimerViewModel(
    private val timerModel: TimerModel
) : ViewModel() {

    private val mutableLiveData =
        MutableLiveData<TimerViewData>()

    val viewDataLiveData: LiveData<TimerViewData> = mutableLiveData

    init {
        updateLiveData()
        timerModel.workTimeChangedListener = { updateLiveData() }
    }

    private fun updateLiveData() {
        mutableLiveData.postValue(
            TimerViewData(
                setsText = timerModel.totalSets.toString(),
                workTimeText = DurationFormatter.toDisplayString(timerModel.currentWorkTime),
                restTimeText = DurationFormatter.toDisplayString(timerModel.currentRestTime)
            )
        )
    }

    fun start() {
        timerModel.start()
    }

    fun pause() {
        timerModel.pause()
    }

}
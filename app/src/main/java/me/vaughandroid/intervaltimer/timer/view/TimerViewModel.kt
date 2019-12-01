package me.vaughandroid.intervaltimer.timer.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.vaughandroid.intervaltimer.time.DurationFormatter
import me.vaughandroid.intervaltimer.timer.domain.TimerModel

class TimerViewModel(
    timerModel: TimerModel
) : ViewModel() {

    private val mutableLiveData =
        MutableLiveData<TimerViewData>()

    val viewDataLiveData: LiveData<TimerViewData> = mutableLiveData

    init {
        mutableLiveData.value = TimerViewData(
            setsText = timerModel.currentSets.toString(),
            workTimeText = DurationFormatter.toDisplayString(timerModel.currentWorkTime),
            restTimeText= DurationFormatter.toDisplayString(timerModel.currentRestTime)
        )
    }

}
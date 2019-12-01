package me.vaughandroid.intervaltimer.timer.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_timer.*
import me.vaughandroid.intervaltimer.IntervalTimerApplication
import me.vaughandroid.intervaltimer.R

class TimerFragment : Fragment() {

    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timerViewModel = IntervalTimerApplication.getViewModel(
            this,
            TimerViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<TimerViewData> { viewData -> updateViews(viewData) }
        timerViewModel.viewDataLiveData.observe(this, observer)
    }

    private fun updateViews(viewData: TimerViewData) {
        setsTextView.text = viewData.setsText
        workTimeTextView.text = viewData.workTimeText
        restTimeTextView.text = viewData.restTimeText
    }

}

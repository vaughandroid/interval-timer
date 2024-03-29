package me.vaughandroid.intervaltimer.configuration.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_configuration.*
import me.vaughandroid.intervaltimer.IntervalTimerApplication
import me.vaughandroid.intervaltimer.Screen
import me.vaughandroid.intervaltimer.R

class ConfigurationFragment : Fragment() {

    var navigationEventHandler: ((Screen) -> Unit)? = null

    private lateinit var configurationViewModel: ConfigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configurationViewModel = IntervalTimerApplication.getViewModel(
            this,
            ConfigurationViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<ConfigurationViewData> { viewData -> updateViews(viewData) }
        configurationViewModel.viewDataLiveData.observe(this, observer)

        setsNumberChooserView.incrementListener = { configurationViewModel.incrementSets() }
        setsNumberChooserView.decrementListener = { configurationViewModel.decrementSets() }
        workTimeNumberChooserView.incrementListener = { configurationViewModel.incrementWorkTime() }
        workTimeNumberChooserView.decrementListener = { configurationViewModel.decrementWorkTime() }
        restTimeNumberChooserView.incrementListener = { configurationViewModel.incrementRestTime() }
        restTimeNumberChooserView.decrementListener = { configurationViewModel.decrementRestTime() }

        doneButton.setOnClickListener {
            navigationEventHandler?.invoke(Screen.TIMER)
        }
    }

    private fun updateViews(configurationViewData: ConfigurationViewData) {
        setsNumberChooserView.value = configurationViewData.setsText
        workTimeNumberChooserView.value = configurationViewData.workTimeText
        restTimeNumberChooserView.value = configurationViewData.restTimeText
    }

}

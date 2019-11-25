package me.vaughandroid.intervaltimer.configuration.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_configuration.*
import me.vaughandroid.intervaltimer.NavigationEvent
import me.vaughandroid.intervaltimer.R
import me.vaughandroid.intervaltimer.di.ViewModelFactory

class ConfigurationFragment : Fragment() {

    var navigationEventHandler: ((NavigationEvent) -> Unit)? = null

    private lateinit var configurationViewModel: ConfigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configurationViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory
        )[ConfigurationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<ConfigurationViewData> { viewData -> updateValues(viewData) }
        configurationViewModel.viewDataLiveData.observe(this, observer)

        setsNumberChooserView.incrementListener = { configurationViewModel.incrementSets() }
        setsNumberChooserView.decrementListener = { configurationViewModel.decrementSets() }
        workTimeNumberChooserView.incrementListener = { configurationViewModel.incrementWorkTime() }
        workTimeNumberChooserView.decrementListener = { configurationViewModel.decrementWorkTime() }
        restTimeNumberChooserView.incrementListener = { configurationViewModel.incrementRestTime() }
        restTimeNumberChooserView.decrementListener = { configurationViewModel.decrementRestTime() }

        doneButton.setOnClickListener {
            navigationEventHandler?.invoke(NavigationEvent.TIMER)
        }
    }

    private fun updateValues(configurationViewData: ConfigurationViewData) {
        setsNumberChooserView.value = configurationViewData.setsText
        workTimeNumberChooserView.value = configurationViewData.workTimeText
        restTimeNumberChooserView.value = configurationViewData.restTimeText
    }

}

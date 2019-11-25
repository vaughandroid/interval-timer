package me.vaughandroid.intervaltimer.configuration.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_configuration.*
import me.vaughandroid.intervaltimer.NavigationEvent
import me.vaughandroid.intervaltimer.R
import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.configuration.domain.ConfigurationModel
import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.DurationFormatter

class ConfigurationFragment : Fragment() {

    companion object {

        private const val KEY_INITIAL_CONFIGURATION = "key_initial_configuration"

        fun withInitialConfiguration(initialConfiguration: Configuration) =
            ConfigurationFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_INITIAL_CONFIGURATION, initialConfiguration)
                }
            }

    }

    var navigationEventHandler: ((NavigationEvent) -> Unit)? = null

    private val initialConfiguration: Configuration
        get() = arguments?.getSerializable(KEY_INITIAL_CONFIGURATION) as? Configuration
            ?: Configuration()

    private lateinit var configurationModel: ConfigurationModel
    private val configurationPresenter =
        ConfigurationPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configurationModel =
            ConfigurationModel(
                initialConfiguration
            )
        configurationModel.onConfigurationChanged = { configuration ->
            configurationPresenter.onConfigurationChanged(configuration)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurationPresenter.viewDataListener = { configurationViewData ->
            updateValues(configurationViewData)
        }

        setsNumberChooserView.incrementListener = { configurationModel.incrementSets() }
        setsNumberChooserView.decrementListener = { configurationModel.decrementSets() }
        workTimeNumberChooserView.incrementListener = { configurationModel.incrementWorkTime() }
        workTimeNumberChooserView.decrementListener = { configurationModel.decrementWorkTime() }
        restTimeNumberChooserView.incrementListener = { configurationModel.incrementRestTime() }
        restTimeNumberChooserView.decrementListener = { configurationModel.decrementRestTime() }

        doneButton.setOnClickListener {
            navigationEventHandler?.invoke(
                NavigationEvent.Timer(configurationModel.currentConfiguration)
            )
        }

        configurationPresenter.onConfigurationChanged(configurationModel.currentConfiguration)
    }

    private fun updateValues(configurationViewData: ConfigurationViewData) {
        setsNumberChooserView.value = configurationViewData.setsText
        workTimeNumberChooserView.value = configurationViewData.workTimeText
        restTimeNumberChooserView.value = configurationViewData.restTimeText
    }

}

private fun Duration.toDisplayString(): String = DurationFormatter.toDisplayString(this)

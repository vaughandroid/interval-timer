package me.vaughandroid.intervaltimer.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_configuration.*
import me.vaughandroid.intervaltimer.NavigationEvent
import me.vaughandroid.intervaltimer.R
import me.vaughandroid.intervaltimer.time.Duration

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configurationModel = ConfigurationModel(initialConfiguration)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurationModel.onConfigurationChanged = { newConfiguration ->
            updateValues(newConfiguration)
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

        updateValues(configurationModel.currentConfiguration)
    }

    private fun updateValues(currentConfiguration: Configuration) {
        setsNumberChooserView.value = currentConfiguration.sets.toString()
        workTimeNumberChooserView.value = currentConfiguration.workTime.toDisplayString()
        restTimeNumberChooserView.value = currentConfiguration.restTime.toDisplayString()
    }

}

private fun Duration.toDisplayString() = (millis / 1000).toString()

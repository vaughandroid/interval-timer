package me.vaughandroid.intervaltimer.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_configuration.*
import me.vaughandroid.intervaltimer.R
import me.vaughandroid.intervaltimer.time.SecondsDuration

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

    private val initialConfiguration: Configuration
        get() = arguments?.getSerializable(KEY_INITIAL_CONFIGURATION) as? Configuration
            ?: throw IllegalStateException("Missing argument: $KEY_INITIAL_CONFIGURATION")

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

        setsNumberChooserView.incrementListener = {
            configurationModel.incrementSets()
            refreshValues()
        }
        setsNumberChooserView.decrementListener = {
            configurationModel.decrementSets()
            refreshValues()
        }
        workTimeNumberChooserView.incrementListener = {
            configurationModel.incrementWorkTime()
            refreshValues()
        }
        workTimeNumberChooserView.decrementListener = {
            configurationModel.decrementWorkTime()
            refreshValues()
        }
        restTimeNumberChooserView.incrementListener = {
            configurationModel.incrementRestTime()
            refreshValues()
        }
        restTimeNumberChooserView.decrementListener = {
            configurationModel.decrementRestTime()
            refreshValues()
        }
        refreshValues()
    }

    private fun refreshValues() {
        val currentConfiguration = configurationModel.currentConfiguration
        setsNumberChooserView.value = currentConfiguration.sets.toString()
        workTimeNumberChooserView.value = currentConfiguration.workTime.toDisplayString()
        restTimeNumberChooserView.value = currentConfiguration.restTime.toDisplayString()
    }

}

private fun SecondsDuration.toDisplayString() = value.toString()

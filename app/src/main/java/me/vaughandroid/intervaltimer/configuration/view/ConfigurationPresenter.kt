package me.vaughandroid.intervaltimer.configuration.view

import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.DurationFormatter

class ConfigurationPresenter {

    var viewDataListener: ((ConfigurationViewData) -> Unit)? = null

    fun onConfigurationChanged(configuration: Configuration) {
        val viewData = ConfigurationViewData(
            setsText = configuration.sets.toString(),
            workTimeText = DurationFormatter.toDisplayString(
                configuration.workTime
            ),
            restTimeText = DurationFormatter.toDisplayString(
                configuration.restTime
            )
        )
        viewDataListener?.invoke(viewData)
    }

}
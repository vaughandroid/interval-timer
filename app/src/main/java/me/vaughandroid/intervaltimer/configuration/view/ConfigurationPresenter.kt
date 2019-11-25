package me.vaughandroid.intervaltimer.configuration.view

import me.vaughandroid.intervaltimer.configuration.domain.Configuration
import me.vaughandroid.intervaltimer.time.DurationFormatter

object ConfigurationPresenter {

    fun transform(configuration: Configuration): ConfigurationViewData =
        ConfigurationViewData(
            setsText = configuration.sets.toString(),
            workTimeText = DurationFormatter.toDisplayString(
                configuration.workTime
            ),
            restTimeText = DurationFormatter.toDisplayString(
                configuration.restTime
            )
        )

}
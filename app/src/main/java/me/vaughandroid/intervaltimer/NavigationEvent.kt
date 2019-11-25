package me.vaughandroid.intervaltimer

import me.vaughandroid.intervaltimer.configuration.domain.Configuration

sealed class NavigationEvent {

    data class Timer(
        val configuration: Configuration
    ) : NavigationEvent()

}
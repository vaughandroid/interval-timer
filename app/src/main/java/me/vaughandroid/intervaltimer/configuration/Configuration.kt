package me.vaughandroid.intervaltimer.configuration

import me.vaughandroid.intervaltimer.time.SecondsDuration
import me.vaughandroid.intervaltimer.time.seconds

data class Configuration(
    val sets: Int = 1,
    val workTime: SecondsDuration = 1.seconds
)
package me.vaughandroid.intervaltimer.configuration

import me.vaughandroid.intervaltimer.time.SecondsDuration
import me.vaughandroid.intervaltimer.time.seconds
import java.io.Serializable

data class Configuration(
    val sets: Int = 5,
    val workTime: SecondsDuration = 30.seconds,
    val restTime: SecondsDuration = 10.seconds
) : Serializable
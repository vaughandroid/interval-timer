package me.vaughandroid.intervaltimer.configuration

import me.vaughandroid.intervaltimer.time.Duration
import me.vaughandroid.intervaltimer.time.seconds
import java.io.Serializable

data class Configuration(
    val sets: Int = 5,
    val workTime: Duration = 30.seconds,
    val restTime: Duration = 10.seconds
) : Serializable
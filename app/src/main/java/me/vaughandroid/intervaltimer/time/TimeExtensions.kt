package me.vaughandroid.intervaltimer.time

val Int.seconds: SecondsDuration
    get() = SecondsDuration(this)

fun SecondsDuration.coerceAtLeast(minimumValue: SecondsDuration) =
    if (this < minimumValue) minimumValue else this

fun SecondsDuration.coerceAtMost(maximumValue: SecondsDuration) =
    if (this > maximumValue) maximumValue else this

val Int.hours: HoursDuration
    get() = HoursDuration(this)

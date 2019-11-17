package me.vaughandroid.intervaltimer.time

inline class HoursDuration(private val value: Int) {

    val toSeconds: SecondsDuration
        get() = (value * 60 * 60).seconds

}

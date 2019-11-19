package me.vaughandroid.intervaltimer.time

val Int.seconds: Duration
    get() = Duration(this * 1000)

val Int.hours: Duration
    get() = Duration(this * 60 * 60 * 1000)

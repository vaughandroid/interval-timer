package me.vaughandroid.intervaltimer.timer.domain

enum class TimerState {
    READY,
    COUNTING_WORK,
    COUNTING_REST,
    PAUSED_WORK,
    PAUSED_REST
}
package me.vaughandroid.intervaltimer.timer.domain

enum class TimerState {
    READY,
    WORK_RUNNING,
    WORK_PAUSED,
    REST_RUNNING,
    REST_PAUSED
}
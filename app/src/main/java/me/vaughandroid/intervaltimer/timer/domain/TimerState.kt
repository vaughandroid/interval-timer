package me.vaughandroid.intervaltimer.timer.domain

enum class TimerState(
    val segmentType: SegmentType,
    val timerRunningState: TimerRunningState
) {
    READY(SegmentType.READY, TimerRunningState.PAUSED),
    WORK_RUNNING(SegmentType.WORK, TimerRunningState.RUNNING),
    WORK_PAUSED(SegmentType.WORK, TimerRunningState.PAUSED),
    REST_RUNNING(SegmentType.REST, TimerRunningState.RUNNING),
    REST_PAUSED(SegmentType.REST, TimerRunningState.PAUSED)
}
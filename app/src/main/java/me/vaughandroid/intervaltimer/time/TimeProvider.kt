package me.vaughandroid.intervaltimer.time

interface TimeProvider {

    val currentTimeMillis: Long

    val tickSubscribers: MutableList<(Long) -> Unit>

}

package me.vaughandroid.intervaltimer.time

interface TimeProvider {

    val currentTimeMillis: Long

    val tickSubscribers: MutableSet<(Long) -> Unit>

}

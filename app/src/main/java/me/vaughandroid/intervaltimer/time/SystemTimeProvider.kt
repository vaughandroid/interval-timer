package me.vaughandroid.intervaltimer.time

object SystemTimeProvider : TimeProvider {
    
    override val currentTimeMillis: Long
        get() = System.currentTimeMillis()

}

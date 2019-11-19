package me.vaughandroid.intervaltimer.time

data class Duration(
    val millis: Int
) {

    operator fun plus(other: Duration) =
        Duration(this.millis + other.millis)

    operator fun minus(other: Duration) =
        Duration(this.millis - other.millis)

    operator fun compareTo(other: Duration) =
        this.millis - other.millis

    fun coerceAtLeast(minimumValue: Duration) =
        if (this < minimumValue) minimumValue else this

    fun coerceAtMost(maximumValue: Duration) =
        if (this > maximumValue) maximumValue else this

}
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

    val hoursPart : Int
        get() = millis / (1000 * 60 * 60)

    val minutesPart : Int
        get() = (millis / (1000 * 60)) % 60

    val secondsPart : Int
        get() = (millis / 1000) % 60

    fun coerceAtLeast(minimumValue: Duration) =
        if (this < minimumValue) minimumValue else this

    fun coerceAtMost(maximumValue: Duration) =
        if (this > maximumValue) maximumValue else this

}
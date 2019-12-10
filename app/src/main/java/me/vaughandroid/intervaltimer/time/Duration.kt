package me.vaughandroid.intervaltimer.time

data class Duration(
    val millis: Int
) {

    operator fun plus(other: Duration) =
        Duration(this.millis + other.millis)

    operator fun minus(other: Duration) =
        Duration(this.millis - other.millis)

    operator fun unaryMinus() = Duration(-this.millis)

    operator fun compareTo(other: Duration) =
        this.millis - other.millis

    val hoursPart: Int
        get() = millis / (1000 * 60 * 60)

    val minutesPart: Int
        get() = (millis / (1000 * 60)) % 60

    val secondsPart: Int
        get() = (millis / 1000) % 60

    fun coerceIn(minimumValue: Duration, maximumValue: Duration) =
        when {
            minimumValue > maximumValue ->
                throw IllegalArgumentException("Min cannot be greater than max")
            this < minimumValue -> minimumValue
            this > maximumValue -> maximumValue
            else -> this
        }

}
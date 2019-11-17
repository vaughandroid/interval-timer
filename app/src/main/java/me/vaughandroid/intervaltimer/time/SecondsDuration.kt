package me.vaughandroid.intervaltimer.time

inline class SecondsDuration(private val value: Int) {

    operator fun plus(other: SecondsDuration) =
        (this.value + other.value).seconds

    operator fun minus(other: SecondsDuration) =
        (this.value - other.value).seconds

    operator fun compareTo(other: SecondsDuration) =
        this.value - other.value

}
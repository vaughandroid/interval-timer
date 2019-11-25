package me.vaughandroid.intervaltimer.time

object DurationFormatter {
    fun toDisplayString(duration: Duration): String {
        return duration.let {
            val hours = if (it.hoursPart != 0) "${it.hoursPart}:" else ""
            val minutes =
                if (it.hoursPart != 0) "${"%02d".format(it.minutesPart)}:" else "${it.minutesPart}:"
            val seconds = "%02d".format(it.secondsPart)
            "$hours$minutes$seconds"
        }
    }

}
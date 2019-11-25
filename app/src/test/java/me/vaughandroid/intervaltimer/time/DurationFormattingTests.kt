package me.vaughandroid.intervaltimer.time

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DurationFormattingTests(
    private val duration: Duration,
    private val expectedOutput: String
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(Duration(0), "0:00"),
                arrayOf(1.seconds, "0:01"),
                arrayOf(59.seconds, "0:59"),
                arrayOf(60.seconds, "1:00"),
                arrayOf(1.minutes, "1:00"),
                arrayOf(10.minutes, "10:00"),
                arrayOf(59.minutes, "59:00"),
                arrayOf(59.minutes + 59.seconds, "59:59"),
                arrayOf(1.hours, "1:00:00"),
                arrayOf(10.hours, "10:00:00"),
                arrayOf(99.hours + 59.minutes + 59.seconds, "99:59:59"),
                arrayOf(100.hours, "100:00:00")
            )
        }
    }

    @Test
    fun test() {
        val actualOutput = DurationFormatter.toDisplayString(duration)
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }

}


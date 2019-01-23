package parkandrest.parkingmanagement.core.tariff

import com.google.common.math.LongMath

import java.math.RoundingMode
import java.time.Duration

enum class Period {
    HOUR,
    DAY,
    WEEK;

    /**
     * Calculates integer (in mathematical context) rounded up amount of periods contained in Duration.
     * Supported periods are HOUR, DAY, WEEK.
     * Throws UnsupportedOperationException if period is not supported.
     * @param duration java.time.Duration
     * @return rounded up periods amount
     */
    fun toPeriodQuantity(duration: Duration): Long {
        return when (this) {
            HOUR -> toHours(duration)
            DAY -> toDays(duration)
            WEEK -> toWeeks(duration)
            else -> throw UnsupportedOperationException("Period " + this.name + " is currently not supported")
        }
    }

    private fun toWeeks(duration: Duration): Long {
        val daysInWeek: Long = 7
        return LongMath.divide(toDays(duration), daysInWeek, RoundingMode.UP)
    }

    private fun toDays(duration: Duration): Long {
        val days = duration.toDays()
        return days + if (Duration.ofDays(days).minus(duration).isNegative) 1 else 0
    }

    private fun toHours(duration: Duration): Long {
        val hours = duration.toHours()
        return hours + if (Duration.ofHours(hours).minus(duration).isNegative) 1 else 0
    }
}

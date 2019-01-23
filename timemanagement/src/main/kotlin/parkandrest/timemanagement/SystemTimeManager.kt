package parkandrest.timemanagement


import java.time.LocalDateTime

object SystemTimeManager {

    private var changedTimeValue: LocalDateTime? = null

    val systemDateTime: LocalDateTime
        get() = changedTimeValue ?: LocalDateTime.now()

    fun maxSystemDateTime(): LocalDateTime {
        return LocalDateTime.of(9999, 12, 31, 23, 59)
    }

    fun changeDateTo(dateTime: LocalDateTime): LocalDateTime {
        if(!dateTime.isBefore(LocalDateTime.now())){
            changedTimeValue = dateTime
            return systemDateTime
        }
        throw TimeManagerException("Can not change time to value from the past")
    }

    fun incrementSystemDateTimeByHours(hours: Long): LocalDateTime {
        val incrementedDateTime = LocalDateTime.now().plusHours(hours)
        val maxSystemDate = maxSystemDateTime()
        if (incrementedDateTime.isAfter(maxSystemDate)) {
            throw TimeManagerException("Incremented time value ($incrementedDateTime) is bigger than maximal supported value ($maxSystemDate)")
        }
        changedTimeValue = incrementedDateTime
        return systemDateTime
    }

    fun restoreSystemDateTimeToCurrentMoment(): LocalDateTime {
        changedTimeValue = null
        return systemDateTime
    }
}

package parkandrest.timemanagement

import org.junit.jupiter.api.Test

import java.time.LocalDateTime

import org.junit.jupiter.api.Assertions.*

internal class SystemTimeManagerTest {

    @Test
    fun maxLocalDateTime() {
        assertEquals(
                SystemTimeManager.maxSystemDateTime()
                        .compareTo(LocalDateTime.of(9999, 12, 31, 23, 59)),
                0
        )
    }

    @Test
    fun shouldRestoreSystemDateTimeToCurrentMoment() {
        SystemTimeManager.restoreSystemDateTimeToCurrentMoment()
        assertTrue(SystemTimeManager.systemDateTime.isBefore(LocalDateTime.now().plusMinutes(1)))
        assertTrue(SystemTimeManager.systemDateTime.isAfter(LocalDateTime.now().minusMinutes(1)))
    }

    @Test
    fun shouldIncrementSystemDateTime() {
        SystemTimeManager.restoreSystemDateTimeToCurrentMoment()
        SystemTimeManager.incrementSystemDateTimeByHours(2)
        assertTrue(SystemTimeManager.systemDateTime.isAfter(LocalDateTime.now().plusHours(1)))
        assertTrue(SystemTimeManager.systemDateTime.isBefore(LocalDateTime.now().plusHours(3)))
    }

    @Test
    fun shouldChangeSystemDateTimeToGiven(){
        SystemTimeManager.restoreSystemDateTimeToCurrentMoment()
        SystemTimeManager.changeDateTo(LocalDateTime.now().plusHours(10))
        assertTrue(SystemTimeManager.systemDateTime.isAfter(LocalDateTime.now().plusHours(9)))
        assertTrue(SystemTimeManager.systemDateTime.isBefore(LocalDateTime.now().plusHours(11)))
    }

    @Test
    fun shouldThrowTimeManagerExceptionBecauseWeIncrementedTooMuch() {
        SystemTimeManager.restoreSystemDateTimeToCurrentMoment()
        assertThrows(TimeManagerException::class.java) { SystemTimeManager.incrementSystemDateTimeByHours(70071240) }
    }

    @Test
    fun shouldThrowTimeManagerExceptionBecauseWeSetPastValue() {
        SystemTimeManager.restoreSystemDateTimeToCurrentMoment()
        assertThrows(TimeManagerException::class.java) { SystemTimeManager.changeDateTo(LocalDateTime.now().minusSeconds(1)) }
    }
}
package parkandrest.parkingmanagement.core.tariff


import spock.lang.Specification

import java.time.Duration

class PeriodSpec extends Specification {

    def "should round up quantities"() {
        given:
        def hourPeriod = Period.HOUR
        def dayPeriod = Period.DAY
        def weekPeriod = Period.WEEK
        when:
        def hoursQuantity = hourPeriod.toPeriodQuantity(Duration.ofMinutes(121).minusSeconds(59).minusMillis(99).minusNanos(99))
        def daysQuantity = dayPeriod.toPeriodQuantity(Duration.ofHours(49).minusMinutes(59).minusSeconds(59).minusMillis(99).minusNanos(99))
        def weeksQuantity = weekPeriod.toPeriodQuantity(Duration.ofDays(15).minusHours(23).minusMinutes(59).minusSeconds(59).minusMillis(99).minusNanos(99))
        then:
        hoursQuantity == 3
        daysQuantity == 3
        weeksQuantity == 3
    }

    def "should give exact values"(){
        given:
        def hourPeriod = Period.HOUR
        def dayPeriod = Period.DAY
        def weekPeriod = Period.WEEK
        when:
        def hoursQuantity = hourPeriod.toPeriodQuantity(Duration.ofMinutes(120))
        def daysQuantity = dayPeriod.toPeriodQuantity(Duration.ofHours(48))
        def weeksQuantity = weekPeriod.toPeriodQuantity(Duration.ofDays(14))
        then:
        hoursQuantity == 2
        daysQuantity == 2
        weeksQuantity == 2
    }
}

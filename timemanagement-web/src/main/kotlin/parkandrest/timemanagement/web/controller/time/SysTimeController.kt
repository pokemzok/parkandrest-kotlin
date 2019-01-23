package parkandrest.timemanagement.web.controller.time

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import parkandrest.timemanagement.web.response.Response
import parkandrest.timemanagement.SystemTimeManager
import parkandrest.events.ChangedTimeEvent
import parkandrest.events.RestoredTimeEvent
import parkandrest.timemanagement.web.rabbit.EventProducer

/**
 * Controller which allows system time modification. Strictly for testing purpose!
 * time.changing.enabled property should be set to false on production machine
 */
@RestController
@RequestMapping("/time/")
class SysTimeController(private val producer: EventProducer) {

    companion object {
        private val log = LoggerFactory.getLogger(SysTimeController::class.java)
    }

    /**
     * Allows to increment system time by hours
     * @param request parkandrest.timemanagement.web.controller.time.SysTimeChangeRequest
     * @return parkandrest.timemanagement.web.response.Response with parkandrest.timemanagement.web.controller.time.SysTimeResponse
     */
    @RequestMapping(value = ["increment"], method = [RequestMethod.POST], consumes = ["application/json"])
    fun incrementTime(@RequestBody request: SysTimeChangeRequest): Response<SysTimeResponse> {
        log.info("Increment time request received. Adding {} hours to system time.", request.hours)
        val event = ChangedTimeEvent(SystemTimeManager.incrementSystemDateTimeByHours(request.hours))
        log.info("Sending change time event to subscribers", request.hours)
        producer.publish(event)
        return Response(
                SysTimeResponse(
                        event.dateTime
                )
        )
    }

    /**
     * Restore System time to current moment in time
     * @return parkandrest.timemanagement.web.response.Response with parkandrest.timemanagement.web.controller.time.SysTimeResponse
     */
    @RequestMapping(value = ["restore"], method = [RequestMethod.GET])
    fun restore(): Response<SysTimeResponse> {
        log.info("System time restoration request received. Restoring system time.")
        producer.publish(RestoredTimeEvent())
        log.info("Sending restore time event to subscribers")
        val currentTime = SystemTimeManager.restoreSystemDateTimeToCurrentMoment()
        return Response(
                SysTimeResponse(
                        currentTime
                )
        )
    }

}

package parkandrest.parkingmanagement.web.rabbit.time.restored

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import parkandrest.events.RestoredTimeEvent
import parkandrest.timemanagement.SystemTimeManager

@Component
class RestoredTimeEventConsumer{

    @RabbitListener
    fun receiveEvent(event: RestoredTimeEvent){
        log.info("Received Message: $event")
        SystemTimeManager.restoreSystemDateTimeToCurrentMoment()
    }

    companion object {
        private val log = LoggerFactory.getLogger(RestoredTimeEventConsumer::class.java)
    }
}
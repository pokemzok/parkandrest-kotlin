package parkandrest.security.web.rabbit.time.changed

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import parkandrest.events.ChangedTimeEvent
import parkandrest.timemanagement.SystemTimeManager

@Component
class ChangedTimeEventConsumer{

    @RabbitListener
    fun receiveEvent(event: ChangedTimeEvent){
        log.info("Received Message: $event")
        SystemTimeManager.changeDateTo(event.dateTime)
    }

    companion object {
        private val log = LoggerFactory.getLogger(ChangedTimeEventConsumer::class.java)
    }
}
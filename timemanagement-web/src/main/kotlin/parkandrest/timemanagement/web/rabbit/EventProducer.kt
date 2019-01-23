package parkandrest.timemanagement.web.rabbit

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import parkandrest.events.RabbitEvent

@Service
class EventProducer (
        private val rabbitTemplate: RabbitTemplate,
        @Value("\${exchange.name}") private val topicExchangeName: String
) {

    fun publish(rabbitEvent: RabbitEvent) {
        rabbitTemplate.convertAndSend(topicExchangeName, rabbitEvent.routingKey(), rabbitEvent)
    }

}
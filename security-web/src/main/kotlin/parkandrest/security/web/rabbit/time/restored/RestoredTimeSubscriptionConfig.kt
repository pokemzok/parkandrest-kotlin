package parkandrest.security.web.rabbit.time.restored

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import parkandrest.events.ChangedTimeEvent
import parkandrest.events.RestoredTimeEvent
import parkandrest.security.web.rabbit.time.restored.RestoredTimeEventConsumer


@Configuration
class RestoredTimeSubscriptionConfig (
        @Value("\${security.restored-time.queue}") private val queueName: String
) {

    @Bean
    fun restoredTimeQueue(): Queue {
        return Queue(queueName, false)
    }

    @Bean
    fun restoredTimeBinding(restoredTimeQueue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(restoredTimeQueue).to(exchange).with(RestoredTimeEvent::class.simpleName)
    }

    @Bean
    fun restoredTimeListenerContainer(connectionFactory: ConnectionFactory,
                                      restoredTimeListenerAdapter: MessageListenerAdapter): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(restoredTimeListenerAdapter)
        return container
    }

    @Bean
    fun restoredTimeListenerAdapter(eventConsumer: RestoredTimeEventConsumer, jsonMessageConverter: MessageConverter): MessageListenerAdapter {
        val adapter = MessageListenerAdapter(eventConsumer, "receiveEvent")
        adapter.setMessageConverter(jsonMessageConverter)
        return adapter
    }

}
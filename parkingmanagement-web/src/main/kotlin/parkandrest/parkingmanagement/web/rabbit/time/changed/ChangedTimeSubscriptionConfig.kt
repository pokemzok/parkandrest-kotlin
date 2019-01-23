package parkandrest.parkingmanagement.web.rabbit.time.changed

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


@Configuration
class ChangedTimeSubscriptionConfig (
        @Value("\${parkingmanagement.changed-time.queue}") private val queueName: String
) {

    @Bean
    fun changedTimeQueue(): Queue {
        return Queue(queueName, false)
    }

    @Bean
    fun changedTimeBinding(changedTimeQueue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(changedTimeQueue).to(exchange).with(ChangedTimeEvent::class.simpleName)
    }

    @Bean
    fun changedTimeListenerContainer(connectionFactory: ConnectionFactory,
                                     changedTimeListenerAdapter: MessageListenerAdapter): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(changedTimeListenerAdapter)
        return container
    }

    @Bean
    fun changedTimeListenerAdapter(eventConsumer: ChangedTimeEventConsumer, jsonMessageConverter: MessageConverter): MessageListenerAdapter {
        val adapter = MessageListenerAdapter(eventConsumer, "receiveEvent")
        adapter.setMessageConverter(jsonMessageConverter)
        return adapter
    }

}
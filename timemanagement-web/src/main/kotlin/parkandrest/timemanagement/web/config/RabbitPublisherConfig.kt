package parkandrest.timemanagement.web.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitPublisherConfig {

    @Bean
    fun exchange( @Value("\${exchange.name}") topicExchangeName: String): TopicExchange {
        return TopicExchange(topicExchangeName)
    }

    @Bean
    fun jsonMessageConverter(objectMapper: ObjectMapper): MessageConverter {
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun template(connectionFactory: ConnectionFactory, topicExchange: TopicExchange, jsonMessageConverter: MessageConverter): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.exchange = topicExchange.name
        template.messageConverter = jsonMessageConverter
        return template
    }

}
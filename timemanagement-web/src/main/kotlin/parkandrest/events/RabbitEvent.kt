package parkandrest.events

interface RabbitEvent {
    fun routingKey(): String
}
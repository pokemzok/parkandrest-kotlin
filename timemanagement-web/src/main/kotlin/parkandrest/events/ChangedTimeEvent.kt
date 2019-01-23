package parkandrest.events

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class ChangedTimeEvent(
        var dateTime: LocalDateTime,
        var uuid: UUID = UUID.randomUUID(),
        var routingKey: String = ChangedTimeEvent::class.simpleName!!
) : Serializable, RabbitEvent {

    override fun routingKey(): String {
        return routingKey
    }

}
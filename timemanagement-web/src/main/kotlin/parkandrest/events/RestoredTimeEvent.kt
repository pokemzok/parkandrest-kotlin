package parkandrest.events

import java.io.Serializable
import java.util.*

data class RestoredTimeEvent(
        var uuid: UUID = UUID.randomUUID(),
        var routingKey: String = RestoredTimeEvent::class.simpleName!!
) : Serializable, RabbitEvent {

    override fun routingKey(): String {
        return routingKey
    }

}
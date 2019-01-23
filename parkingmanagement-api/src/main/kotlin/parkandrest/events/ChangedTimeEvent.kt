package parkandrest.events

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class ChangedTimeEvent(
        var dateTime: LocalDateTime,
        var uuid: UUID,
        var routingKey: String
) : Serializable
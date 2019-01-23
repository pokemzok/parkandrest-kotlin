package parkandrest.security.api.usermanagement

import java.time.LocalDateTime

data class FilteredUserResponse(
        val username: String,
        val registrationDateTime: LocalDateTime,
        val isActive: Boolean,
        val authorities: List<String>
)

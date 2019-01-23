package parkandrest.security.api.usermanagement

import java.time.LocalDateTime


data class NewUserResponse (
     val username: String?,
     val isActive: Boolean?,
     val registrationDateTime: LocalDateTime?
)

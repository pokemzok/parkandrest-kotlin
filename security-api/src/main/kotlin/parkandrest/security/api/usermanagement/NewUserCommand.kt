package parkandrest.security.api.usermanagement

data class NewUserCommand(
        var username: String,
        var password: String,
        var authorities: List<String>,
        var isActive: Boolean
)

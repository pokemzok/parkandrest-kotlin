package parkandrest.security.api.usermanagement

data class FilterUsersQuery(
        var page: Int,
        var size: Int,
        var username: String?,
        var authority: String?,
        var isActive: Boolean?
)
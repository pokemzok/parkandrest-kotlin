package parkandrest.security.core.usermanagement

import org.springframework.data.domain.Page
import parkandrest.security.api.usermanagement.FilteredUserResponse

internal object AppUserAssembler {

    fun toFilteredUserResponsePages(users: Page<AppUser>) : Page<FilteredUserResponse>{
        return users.map{ toFilteredUserResponse(it) }
    }

    private fun toFilteredUserResponse(users: AppUser): FilteredUserResponse {
        return FilteredUserResponse(
                username = users.username,
                isActive = users.isActive,
                authorities = users.authorities.map { it.authority },
                registrationDateTime = users.registrationDateTime
        )
    }


}

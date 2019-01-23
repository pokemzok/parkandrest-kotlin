package parkandrest.security.core.usermanagement

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import parkandrest.security.api.usermanagement.FilterUsersQuery
import parkandrest.security.api.usermanagement.FilteredUserResponse

@Service
class AppUserQueryService(private val appUserRepository: AppUserRepository) {

    fun filterUsers(usersQuery: FilterUsersQuery): Page<FilteredUserResponse> {
        return AppUserAssembler.toFilteredUserResponsePages(
                appUserRepository.findAll(
                        AppUserSpecification(usersQuery).predicate(),
                        PageRequest.of(
                                usersQuery.page,
                                usersQuery.size,
                                Sort.unsorted()
                        )
                )
        )

    }
}

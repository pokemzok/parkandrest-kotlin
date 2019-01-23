package parkandrest.security.web.controller.user

import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import parkandrest.security.api.usermanagement.FilterUsersQuery
import parkandrest.security.api.usermanagement.FilteredUserResponse
import parkandrest.security.core.usermanagement.AppUserQueryService
import parkandrest.security.web.response.Response

@RestController
@RequestMapping(value = ["users"])
@PreAuthorize("hasAuthority('ADMIN')")
class UsersController(private val queryService: AppUserQueryService) {

    @PostMapping()
    fun users(@RequestBody usersQuery: FilterUsersQuery): Response<Page<FilteredUserResponse>> {
       return Response(queryService.filterUsers(usersQuery))
    }
}

package parkandrest.security.web.controller.user

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import parkandrest.security.api.usermanagement.NewUserCommand
import parkandrest.security.api.usermanagement.NewUserResponse
import parkandrest.security.api.usermanagement.UserActionCommand
import parkandrest.security.core.usermanagement.AppUserRegistrationService
import parkandrest.security.core.usermanagement.UserManagementService
import parkandrest.security.web.response.EmptyContent
import parkandrest.security.web.response.Response

@RestController
@RequestMapping(value = ["user/"])
@PreAuthorize("hasAuthority('ADMIN')")
class UserManagementController (private val appUserRegistrationService: AppUserRegistrationService, private val userManagementService: UserManagementService){

    @PutMapping("sign-up")
    fun signUp(@RequestBody newUserCommand: NewUserCommand): Response<NewUserResponse> {
        return Response(
                appUserRegistrationService.register(newUserCommand)
        )
    }

    @GetMapping("exist/{username}")
    fun exist(@PathVariable username: String): Response<EmptyContent> {
        appUserRegistrationService.checkIfUserAlreadyExists(username)
        return Response(
                EmptyContent()
        )
    }

    @PutMapping("activate")
    fun activate (@RequestBody  command: UserActionCommand): Response<EmptyContent> {
        userManagementService.activate(command)
        return Response(EmptyContent())
    }

    @PutMapping("deactivate")
    fun deactivate (@RequestBody  command: UserActionCommand): Response<EmptyContent> {
        userManagementService.deactivate(command)
        return Response(EmptyContent())
    }
}

package parkandrest.security.core.usermanagement

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parkandrest.security.api.usermanagement.NewUserCommand
import parkandrest.security.api.usermanagement.NewUserResponse
import parkandrest.security.api.usermanagement.UserAlreadyExistsException

@Service
@Transactional
    class AppUserRegistrationService(private val appUserRepository: AppUserRepository, private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    fun register(command: NewUserCommand): NewUserResponse {
        checkIfUserAlreadyExists(command.username)
        val appUser = AppUser(
                command.username,
                bCryptPasswordEncoder.encode(command.password),
                command.isActive
        )
        command.authorities.forEach { appUser.addRole(UserAuthority(appUser, Authority.valueOf(it))) }
        appUserRepository.save(appUser)
        return NewUserResponse(
                appUser.username,
                appUser.isActive,
                appUser.registrationDateTime
        )
    }

    fun checkIfUserAlreadyExists(username: String) {
        appUserRepository.findByUsername(username).ifPresent { throw UserAlreadyExistsException(arrayOf(username), "User with username $username already exists") }
    }
}

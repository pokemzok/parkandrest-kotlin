package parkandrest.security.core.usermanagement

import org.springframework.stereotype.Service
import parkandrest.security.api.usermanagement.NoUserFoundException
import parkandrest.security.api.usermanagement.UserActionCommand

@Service
class UserManagementService (private val userRepo: AppUserRepository) {

    fun activate(command: UserActionCommand){
        val user = userRepo.findByUsername(command.username).orElseThrow{
            NoUserFoundException(arrayOf(command.username), "No user was found for the id value of " + command.username)
        }
        user.activate()
        userRepo.save(user)
    }

    fun deactivate(command: UserActionCommand){
        val user = userRepo.findByUsername(command.username).orElseThrow{
            NoUserFoundException(arrayOf(command.username), "No user was found for the id value of " + command.username)
        }
        user.deactivate()
        userRepo.save(user)
    }
}
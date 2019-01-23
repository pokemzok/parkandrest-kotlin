package parkandrest.security.core.usermanagement

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val appUserRepository: AppUserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return appUserRepository.findByUsername(username).orElseThrow { UsernameNotFoundException("Username was not found in repository") }
    }
}

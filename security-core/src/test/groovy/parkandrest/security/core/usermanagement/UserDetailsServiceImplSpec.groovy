package parkandrest.security.core.usermanagement

import org.springframework.security.core.userdetails.UsernameNotFoundException
import parkandrest.security.core.usermanagement.AppUser
import parkandrest.security.core.usermanagement.AppUserRepository
import parkandrest.security.core.usermanagement.UserAuthority
import parkandrest.security.core.usermanagement.UserDetailsServiceImpl
import parkandrest.timemanagement.SystemTimeManager
import spock.lang.Specification

class UserDetailsServiceImplSpec extends Specification {

    def appRepositoryMock = Mock(AppUserRepository.class)
    def userDetailsService = new UserDetailsServiceImpl(appRepositoryMock)
    def systimeManager = new SystemTimeManager()
    
    def "should succesfully load user by it's username"() {
        given:
        def username = "username"
        and:
        appRepositoryMock.findByUsername(username) >> Optional.of(new AppUser(username,  "password", true, systimeManager.systemDateTime, new HashSet<UserAuthority>()))
        when:
        def result = userDetailsService.loadUserByUsername(username)
        then:
        result != null
        result.username == username
    }

    def "should throw UsernameNotFoundException load user by it's username"() {
        given:
        def username = "username"
        and:
        appRepositoryMock.findByUsername(username) >> Optional.empty()
        when:
        userDetailsService.loadUserByUsername(username)
        then:
        thrown UsernameNotFoundException
    }

}

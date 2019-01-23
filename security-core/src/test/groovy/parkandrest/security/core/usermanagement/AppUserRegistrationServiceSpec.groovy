package parkandrest.security.core.usermanagement

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import parkandrest.security.api.usermanagement.NewUserCommand
import parkandrest.security.api.usermanagement.NewUserResponse
import parkandrest.security.api.usermanagement.UserAlreadyExistsException
import parkandrest.timemanagement.SystemTimeManager
import spock.lang.Specification

class AppUserRegistrationServiceSpec extends Specification {

    def appUserRepository = Mock(AppUserRepository.class)
    def bCryptPasswordEncoder = Mock(BCryptPasswordEncoder.class)
    def appUserRegistrationService =  new AppUserRegistrationService(appUserRepository, bCryptPasswordEncoder)
    def systimeManager = new SystemTimeManager()

    def "should register new user"(){
        given:
        def request = new NewUserCommand("username", "password", Authority.values().collect{it.name()}, true)
        def encodedPassword = "encodedPassword"
        and:
        appUserRepository.findByUsername(request.username) >> Optional.empty()
        appUserRepository.save(_ as AppUser)>> new AppUser(request.username, encodedPassword, request.isActive, systimeManager.systemDateTime, new HashSet<UserAuthority>())
        and:
        bCryptPasswordEncoder.encode(request.password) >> encodedPassword
        when:
        def result = appUserRegistrationService.register(request)
        then:
        result == new NewUserResponse(request.username, request.isActive, result.registrationDateTime)
        result.registrationDateTime != null
    }

    def "should throw UserAlreadyExistsException because there is already user with that username in repository"(){
        given:
        def request = new NewUserCommand("username", "password", new ArrayList<String>(), true)
        and:
        appUserRepository.findByUsername(request.username) >> Optional.of(new AppUser(request.username, "encodedPassword", request.isActive, systimeManager.systemDateTime, new HashSet<UserAuthority>()))
        when:
        appUserRegistrationService.register(request)
        then:
        thrown UserAlreadyExistsException
    }

    def "should throw IllegalArgumentException because user authorities are incorrect"(){
        given:
        def request = new NewUserCommand("username", "password", Arrays.asList("NULL", "ZKJDA"), true)
        def encodedPassword = "encodedPassword"
        and:
        appUserRepository.findByUsername(request.username) >> Optional.empty()
        and:
        bCryptPasswordEncoder.encode(request.password) >> encodedPassword
        when:
        appUserRegistrationService.register(request)
        then:
        thrown IllegalArgumentException
    }
}

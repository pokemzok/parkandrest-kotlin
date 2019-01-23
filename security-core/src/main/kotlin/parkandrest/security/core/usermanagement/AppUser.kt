package parkandrest.security.core.usermanagement

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import parkandrest.timemanagement.SystemTimeManager
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "APP_USER")
class AppUser(

        @Column(name = "USERNAME", unique = true)
        private val username: String,

        @Column(name = "PASSWORD")
        private val password: String,

        @Column(name = "IS_ACTIVE")
        internal var isActive: Boolean,

        @Column(name = "REGISTRATION_DATE_TIME")
        val registrationDateTime: LocalDateTime = SystemTimeManager.systemDateTime,

        @OneToMany(mappedBy = "appUser", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val authorities: MutableSet<UserAuthority> = mutableSetOf()

) : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set

    internal fun addRoles(authorities: Collection<UserAuthority>) {
        this.authorities.addAll(authorities)
    }

    internal fun addRole(authority: UserAuthority) {
        this.authorities.add(authority)
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return isActive
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    internal fun activate(){
        this.isActive = true
    }

    internal fun deactivate(){
        this.isActive = false
    }
}

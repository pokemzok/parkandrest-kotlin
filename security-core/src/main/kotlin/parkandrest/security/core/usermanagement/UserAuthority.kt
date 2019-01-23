package parkandrest.security.core.usermanagement

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "USER_AUTHORITY")
class UserAuthority(
        @ManyToOne
        @JoinColumn(name = "APP_USER_ID", nullable = false)
        val appUser: AppUser,

        @Column(name = "ROLE", length = 50, nullable = false)
        @Enumerated(value = EnumType.STRING)
        private val authority: Authority
) : GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set

    override fun getAuthority(): String {
        return authority.name
    }

}

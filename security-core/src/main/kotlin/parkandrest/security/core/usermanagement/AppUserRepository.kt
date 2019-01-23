package parkandrest.security.core.usermanagement

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import parkandrest.security.core.usermanagement.AppUser
import java.util.*

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long>, QuerydslPredicateExecutor<AppUser>{
    fun findByUsername(username: String): Optional<AppUser>
}
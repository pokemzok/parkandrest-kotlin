package parkandrest.security.core.usermanagement

import org.springframework.security.core.GrantedAuthority

class GrantedAuthorityCollection (private val authorities: Collection<GrantedAuthority>) {

    override fun toString(): String {
        return authorities.joinToString(DELIMITER) { it.authority }
    }

    companion object {
        private val DELIMITER = ","
    }
}
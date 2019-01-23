package parkandrest.security.core.usermanagement

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import parkandrest.security.api.usermanagement.FilterUsersQuery

class AppUserSpecification(private val usersQuery: FilterUsersQuery) {

    fun predicate(): Predicate {
        val qUser = QAppUser.appUser
        val predicate = BooleanBuilder()
        return predicate
                .and(
                        createPredicateForNullableParam(usersQuery.isActive) { param -> qUser.isActive.eq(param) }
                )
                .and(
                        createPredicateForNonEmptyString(usersQuery.username) { param -> qUser.username.likeIgnoreCase("%$param%") }
                )
                .and(
                        createPredicateForNonEmptyString(usersQuery.authority) { param -> qUser.authorities.any().authority.eq(Authority.valueOf(param!!)) }
                )
    }

    private fun createPredicateForNonEmptyString(filterValue: String?, predicateFun: (String?) -> Predicate): Predicate {
       if(filterValue.isNullOrEmpty()){
           return BooleanBuilder()
       }
        return predicateFun.invoke(filterValue)
    }

    private fun <T> createPredicateForNullableParam(filterValue: T?, predicateFun: (T?) -> Predicate): Predicate {
        if (filterValue != null) {
            return predicateFun.invoke(filterValue)
        }
        return BooleanBuilder()
    }

}
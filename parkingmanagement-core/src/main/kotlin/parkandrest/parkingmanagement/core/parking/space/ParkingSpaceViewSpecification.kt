package parkandrest.parkingmanagement.core.parking.space

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import parkandrest.parkingmanagement.api.parkingmeter.FilterParkingSpaceRequest


class ParkingSpaceViewSpecification(private val parkingSpaceRequest: FilterParkingSpaceRequest) {

    fun predicate(): Predicate {
        val qParkingSpaceView = QParkingSpaceView.parkingSpaceView
        val predicate = BooleanBuilder()
        return predicate
                .and(
                        createPredicateForNullableParam(parkingSpaceRequest.id) { qParkingSpaceView.id.eq(it) }
                )
                .and(
                        createPredicateForNullableOrEmptyString(parkingSpaceRequest.parkingSpaceStatus) { qParkingSpaceView.status.eq(it) }
                )
                .and(
                        createPredicateForNullableOrEmptyString(parkingSpaceRequest.registration){qParkingSpaceView.registration.likeIgnoreCase("%$it%")}
                )
    }

    private fun createPredicateForNullableOrEmptyString(filterValue: String?, predicateFun: (String?) -> Predicate): Predicate {
        if (filterValue.isNullOrEmpty()) {
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
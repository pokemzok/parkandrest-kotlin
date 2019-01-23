package parkandrest.parkingmanagement.core.parking.space

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import parkandrest.parkingmanagement.core.parking.space.ParkingSpace

@Repository
interface ParkingSpaceRepository : JpaRepository<ParkingSpace, Long>, QuerydslPredicateExecutor<ParkingSpace>
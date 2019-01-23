package parkandrest.parkingmanagement.core.parking.space

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ParkingSpaceViewRepository : JpaRepository<ParkingSpaceView, Long>, QuerydslPredicateExecutor<ParkingSpaceView>
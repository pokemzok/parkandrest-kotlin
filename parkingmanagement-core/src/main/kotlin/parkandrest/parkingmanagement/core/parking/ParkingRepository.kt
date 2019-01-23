package parkandrest.parkingmanagement.core.parking

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParkingRepository : JpaRepository<Parking, Long>
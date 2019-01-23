package parkandrest.parkingmanagement.core.vehicle

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistry

@Repository
interface VehicleRegistryRepository : JpaRepository<VehicleRegistry, Long>{

    fun findByParkingSpaceIdAndIsParkedTrue(parkingSpaceId: Long?): VehicleRegistry?
    fun findByRegistrationAndIsParkedTrue(registration: String?): VehicleRegistry?
}

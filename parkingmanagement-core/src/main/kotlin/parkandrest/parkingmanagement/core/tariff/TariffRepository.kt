package parkandrest.parkingmanagement.core.tariff

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TariffRepository : JpaRepository<Tariff, Long> {

    fun findByParkingId(id: Long?): MutableSet<Tariff>
}

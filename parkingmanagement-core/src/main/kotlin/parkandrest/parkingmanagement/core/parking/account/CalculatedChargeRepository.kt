package parkandrest.parkingmanagement.core.parking.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import parkandrest.parkingmanagement.core.tariff.Tariff

import java.time.LocalDateTime

@Repository
interface CalculatedChargeRepository : JpaRepository<CalculatedCharge, Long> {

    fun getAllByCalculationDateTimeBetweenAndSelectedTariffIn(startDate: LocalDateTime, endDate: LocalDateTime, tariffs: Collection<Tariff>): Set<CalculatedCharge>
}

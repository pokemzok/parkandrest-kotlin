package parkandrest.parkingmanagement.core.parking.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import parkandrest.parkingmanagement.core.tariff.Tariff

import java.time.LocalDateTime

@Repository
interface PaymentRepository : JpaRepository<Payment, Long> {

    fun getAllByPaymentDateTimeBetweenAndSelectedTariffIn(startDate: LocalDateTime, endDate: LocalDateTime, tariffs: Collection<Tariff>): Set<Payment>
}

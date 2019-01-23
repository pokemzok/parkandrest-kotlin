package parkandrest.parkingmanagement.api.parking.account

import java.math.BigDecimal
import java.time.LocalDateTime


data class CalculatedChargeDto(
        var registration: String? = null,
        var charge: BigDecimal? = null,
        var currency: String? = null,
        var calculationDateTime: LocalDateTime? = null,
        var periodsQuantity: Long = 0,
        var period: String? = null
)
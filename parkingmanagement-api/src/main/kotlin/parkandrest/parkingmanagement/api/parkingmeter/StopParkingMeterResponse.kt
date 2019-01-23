package parkandrest.parkingmanagement.api.parkingmeter

import java.math.BigDecimal
import java.time.LocalDateTime

data class StopParkingMeterResponse(
        val startDateTime: LocalDateTime?,
        val endDateTime: LocalDateTime?,
        val amountToPay: BigDecimal?,
        val currency: String?
)

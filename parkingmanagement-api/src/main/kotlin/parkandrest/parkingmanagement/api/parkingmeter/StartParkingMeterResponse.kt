package parkandrest.parkingmanagement.api.parkingmeter

import parkandrest.parkingmanagement.api.parking.account.CalculatedChargeDto
import java.time.LocalDateTime


data class StartParkingMeterResponse(val startDateTime: LocalDateTime? = null, val precalculations: List<CalculatedChargeDto> = emptyList())


package parkandrest.parkingmanagement.api.parkingmeter

import java.time.LocalDateTime

data class FilteredParkingSpaceResponse(var id: Long?, var parkingSpaceStatus: String, var registration: String?, var occupationStartDate: LocalDateTime?)

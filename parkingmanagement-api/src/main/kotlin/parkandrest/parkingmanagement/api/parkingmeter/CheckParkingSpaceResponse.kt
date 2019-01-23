package parkandrest.parkingmanagement.api.parkingmeter

import java.time.LocalDateTime

data class CheckParkingSpaceResponse( val parkingSpaceStatus: String = "", var registration: String?, var occupationStartDateTime: LocalDateTime?)

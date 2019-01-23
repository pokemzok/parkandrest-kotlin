package parkandrest.parkingmanagement.api.vehicle

import java.time.LocalDateTime

data class CheckVehicleResponse(
        val registration: String?,
        val isParked: Boolean,
        val occupationStartDate: LocalDateTime?,
        val parkingSpaceId: Long?
)

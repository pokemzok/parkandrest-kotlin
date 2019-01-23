package parkandrest.parkingmanagement.api.parkingmeter

data class FilterParkingSpaceRequest(
        var id: Long?,
        var parkingSpaceStatus: String?,
        var registration: String?,
        var page: Int,
        var size: Int
)

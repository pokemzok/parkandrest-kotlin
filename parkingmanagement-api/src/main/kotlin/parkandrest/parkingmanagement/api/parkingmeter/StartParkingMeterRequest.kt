package parkandrest.parkingmanagement.api.parkingmeter


data class StartParkingMeterRequest (var parkingSpaceId: Long, var registration: String, var tariffType:String)
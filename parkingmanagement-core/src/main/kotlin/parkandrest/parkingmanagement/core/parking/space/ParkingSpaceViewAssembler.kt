package parkandrest.parkingmanagement.core.parking.space

import org.springframework.data.domain.Page
import parkandrest.parkingmanagement.api.parkingmeter.FilteredParkingSpaceResponse

internal object ParkingSpaceViewAssembler {

    fun toFilteredParkingSpaceResponsePages(parkingSpaceRecords: Page<ParkingSpaceView>): Page<FilteredParkingSpaceResponse> {
        return parkingSpaceRecords.map { toFilteredParkingSpaceResponse(it) }
    }

    fun toFilteredParkingSpaceResponse(parkingSpaceInfo: ParkingSpaceView): FilteredParkingSpaceResponse {
        return FilteredParkingSpaceResponse(
                parkingSpaceInfo.id,
                parkingSpaceInfo.status,
                parkingSpaceInfo.registration,
                parkingSpaceInfo.occupiedFrom
        )
    }

}
package parkandrest.parkingmanagement.core.parking.space

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parkandrest.parkingmanagement.api.parkingmeter.FilterParkingSpaceRequest
import parkandrest.parkingmanagement.api.parkingmeter.FilteredParkingSpaceResponse

@Service
@Transactional
class ParkingSpaceQueryService @Autowired constructor(
        private val parkingSpaceViewRepository: ParkingSpaceViewRepository
) {
    fun filterParkingSpaces(filterRequest: FilterParkingSpaceRequest): Page<FilteredParkingSpaceResponse> {
        return ParkingSpaceViewAssembler.toFilteredParkingSpaceResponsePages(
                parkingSpaceViewRepository.findAll(
                        ParkingSpaceViewSpecification(filterRequest).predicate(),
                        PageRequest.of(
                                filterRequest.page,
                                filterRequest.size,
                                Sort.unsorted()
                        )
                )
        )
    }
}

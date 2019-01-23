package parkandrest.parkingmanagement.web.controller.parkingmeter

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import parkandrest.parkingmanagement.api.parkingmeter.*
import parkandrest.parkingmanagement.api.vehicle.CheckVehicleRequest
import parkandrest.parkingmanagement.api.vehicle.CheckVehicleResponse
import parkandrest.parkingmanagement.core.parking.space.ParkingSpaceQueryService
import parkandrest.parkingmanagement.core.parkingmeter.ParkingMeterService
import parkandrest.parkingmanagement.web.response.Response

@RestController
@RequestMapping(value = ["/parkingmeter/"])
class ParkingMeterController(
        private val parkingMeterService: ParkingMeterService,
        private val parkingSpaceQueryService: ParkingSpaceQueryService) {

    companion object {
        private val log = LoggerFactory.getLogger(ParkingMeterController::class.java)
    }

    /**
     * US.1 As a driver, I want to start the parking meter, so I don’t have to pay the fine for the invalid parking.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.StartParkingMeterRequest
     * @return parkandrest.parkingmanagement.web.response.Response with parkandrest.parkingmanagement.api.parkingmeter.StartParkingMeterResponse or BusinessException
     */

    @PreAuthorize("hasAuthority('DRIVER')")
    @RequestMapping(value = ["start"], method = [RequestMethod.POST], consumes = ["application/json"])
    fun start(@RequestBody request: StartParkingMeterRequest): Response<StartParkingMeterResponse> {
        log.info("Received start parking meter request for parkingSpaceId: {}.", request.parkingSpaceId)
        return Response(
                parkingMeterService.start(request)
        )
    }

    /**
     * US.3 As a driver, I want to stop the parking meter, so that I pay only for the actual parking time
     * US.4 As a driver, I want to know how much I have to pay for parking.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.StopParkingMeterRequest
     * @return parkandrest.parkingmanagement.web.response.Response with parkandrest.parkingmanagement.api.parkingmeter.StopParkingMeterResponse or BusinessException
     */
    @PreAuthorize("hasAuthority('DRIVER')")
    @RequestMapping(value = ["stop"], method = [RequestMethod.POST])
    fun stop(@RequestBody request: StopParkingMeterRequest): Response<StopParkingMeterResponse> {
        log.info("Received stop parking meter request for parkingSpaceId: {}.", request.parkingSpaceId)
        return Response(
                parkingMeterService.stop(request)
        )
    }

    /**
     * As a driver, I want to check if the parking space is free to take.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceRequest
     * @return @return parkandrest.parkingmanagement.web.response.Response with parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceResponse or BusinessException
     */
    @PreAuthorize("hasAuthority('DRIVER')")
    @RequestMapping(value = ["checkParkingSpace"], method = [RequestMethod.POST])
    fun checkParkingSpace(@RequestBody request: CheckParkingSpaceRequest): Response<CheckParkingSpaceResponse> {
        log.info("Received check parking meter request for parkingSpaceId: {}.", request.parkingSpaceId)
        return Response(
                parkingMeterService.check(request)
        )
    }

    /**
     * US.2 implementation. As a parking operator, I want to check if the vehicle has started the parking meter.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceRequest
     * @return @return parkandrest.parkingmanagement.web.response.Response with parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceResponse or BusinessException
     */
    @PreAuthorize("hasAuthority('OPERATOR')")
    @RequestMapping(value = ["checkVehicle"], method = [RequestMethod.POST])
    fun checkVehicleRegistration(@RequestBody request: CheckVehicleRequest): Response<CheckVehicleResponse> {
        log.info("Received check parking meter request for vehicle registration: {}.", request.registration)
        return Response(
                parkingMeterService.check(request)
        )
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @PostMapping("parkingSpaces")
    fun filterParkingSpaces(@RequestBody filterParkingSpaceRequest: FilterParkingSpaceRequest): Response<Page<FilteredParkingSpaceResponse>> {
        log.info("Received filter request for parking spaces")
        return Response(
                parkingSpaceQueryService.filterParkingSpaces(filterParkingSpaceRequest)
        )
    }
}

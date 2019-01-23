package parkandrest.parkingmanagement.core.parkingmeter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parkandrest.parkingmanagement.api.parking.space.ParkingSpaceNotExistsException
import parkandrest.parkingmanagement.api.parkingmeter.*
import parkandrest.parkingmanagement.api.vehicle.CheckVehicleRequest
import parkandrest.parkingmanagement.api.vehicle.CheckVehicleResponse
import parkandrest.parkingmanagement.api.vehicle.NoSuchVehicleParkedException
import parkandrest.parkingmanagement.core.extensions.orElseThrow
import parkandrest.parkingmanagement.core.parking.space.ParkingSpaceRepository
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistry
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistryRepository
import parkandrest.timemanagement.SystemTimeManager

@Service
@Transactional
class ParkingMeterService @Autowired constructor(
        private val parkingSpaceRepository: ParkingSpaceRepository,
        private val vehicleRegistryRepository: VehicleRegistryRepository,
        private val chargeCalculator: ChargeCalculator
) {


    /**
     * US.1 As a driver, I want to start the parking meter, so I don’t have to pay the fine for the invalid parking.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.StartParkingMeterRequest
     * @return parkandrest.parkingmanagement.api.parkingmeter.StartParkingMeterResponse
     * @throws ParkingSpaceNotExistsException if there is no requested parking space in database
     */
    fun start(request: StartParkingMeterRequest): StartParkingMeterResponse {
        val parkingSpace = parkingSpaceRepository
                .findById(request.parkingSpaceId)
                .orElseThrow { ParkingSpaceNotExistsException() }
        val selectedTariff = parkingSpace.parking.tariffsCollection.getTariff(request.tariffType)
        val vehicle = VehicleRegistry(
                parkingSpace,
                selectedTariff,
                request.registration,
                SystemTimeManager.systemDateTime
        )
        vehicleRegistryRepository.save(vehicle)
        return StartParkingMeterResponse(
                vehicle.startDateTime,
                chargeCalculator.preCalculate(vehicle, selectedTariff.precalculationsQuantity)
        )
    }

    /**
     * US.3 As a driver, I want to stop the parking meter, so that I pay only for the actual parking time
     * US.4 As a driver, I want to know how much I have to pay for parking.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.StopParkingMeterRequest
     * @return parkandrest.parkingmanagement.api.parkingmeter.StopParkingMeterResponse
     * @throws NoSuchVehicleParkedException if vehicle is not parked on requested parking space
     */
    fun stop(request: StopParkingMeterRequest): StopParkingMeterResponse {
        val vehicle = vehicleRegistryRepository
                .findByParkingSpaceIdAndIsParkedTrue(request.parkingSpaceId)
                .orElseThrow (NoSuchVehicleParkedException())
        vehicle.leaveParking()
        val calculatedCharge = chargeCalculator.calculateAndSave(vehicle)
        return StopParkingMeterResponse(
                vehicle.startDateTime,
                vehicle.endDateTime,
                calculatedCharge.charge.amount,
                calculatedCharge.charge.currency.name
        )
    }

    /**
     * US.2 As a parking operator, I want to check if the vehicle has started the parking meter.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceRequest
     * @return parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceResponse
     * @throws ParkingSpaceNotExistsException if there is no requested parking space in database
     * @throws NoSuchVehicleParkedException   if vehicle is not parked on requested parking space
     */
    fun check(request: CheckParkingSpaceRequest): CheckParkingSpaceResponse {
        val parkingSpace = parkingSpaceRepository
                .findById(request.parkingSpaceId)
                .orElseThrow { ParkingSpaceNotExistsException() }
        if (!parkingSpace.isOccupied()) {
            return CheckParkingSpaceResponse(parkingSpace.status.name,
                    null, null)
        }
        val vehicle = vehicleRegistryRepository
                .findByParkingSpaceIdAndIsParkedTrue(request.parkingSpaceId)
                .orElseThrow (NoSuchVehicleParkedException())
        return CheckParkingSpaceResponse(parkingSpace.status.name, vehicle.registration, vehicle.startDateTime)
    }

    /**
     * Another US.2 implementation - As a parking operator, I want to check if the vehicle has started the parking meter.
     *
     * @param request parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceRequest
     * @return parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceResponse
     */
    fun check(request: CheckVehicleRequest): CheckVehicleResponse {
        val vehicle = vehicleRegistryRepository.findByRegistrationAndIsParkedTrue(request.registration)
        if (vehicle != null ) {
            return CheckVehicleResponse(
                    vehicle.registration,
                    vehicle.isParked,
                    vehicle.startDateTime,
                    vehicle.parkingSpace.id
            )
        }
        return CheckVehicleResponse(
                request.registration,
                false,
                null,
                null
        )
    }
}

package parkandrest.parkingmanagement.core.parkingmeter

import parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceRequest
import parkandrest.parkingmanagement.api.parkingmeter.CheckParkingSpaceResponse
import parkandrest.parkingmanagement.api.parkingmeter.StartParkingMeterRequest
import parkandrest.parkingmanagement.api.parkingmeter.StopParkingMeterRequest
import parkandrest.parkingmanagement.api.parkingmeter.StopParkingMeterResponse
import spock.lang.Specification
import parkandrest.parkingmanagement.api.parking.account.CalculatedChargeDto
import parkandrest.parkingmanagement.api.parking.space.ParkingSpaceNotExistsException
import parkandrest.parkingmanagement.api.parking.space.UnavailableParkingSpaceException
import parkandrest.parkingmanagement.api.vehicle.CheckVehicleRequest
import parkandrest.parkingmanagement.api.vehicle.CheckVehicleResponse
import parkandrest.parkingmanagement.api.vehicle.NoSuchVehicleParkedException
import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.parking.account.CalculatedCharge
import parkandrest.parkingmanagement.core.parking.space.ParkingSpaceRepository
import parkandrest.parkingmanagement.core.parking.space.ParkingSpaceStatus
import parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistry
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistryRepository

import java.time.LocalDateTime

class ParkingMeterServiceSpec extends Specification {

    def parkingSpaceRepositoryMock = Mock(ParkingSpaceRepository.class)
    def vehicleRegistryRepositoryMock = Mock(VehicleRegistryRepository.class)
    def chargeCalculatorMock = Mock(ChargeCalculator.class)
    def parkingMeterService = new ParkingMeterService(parkingSpaceRepositoryMock, vehicleRegistryRepositoryMock, chargeCalculatorMock)

    def "should throw ParkingSpaceNotExistsException because repository has not found any ParkingSpace"() {
        given:
        parkingSpaceRepositoryMock.findById(_ as Long) >> Optional.empty()
        when:
        parkingMeterService.start(new StartParkingMeterRequest(1l, "registration", "VIP"))
        then:
        thrown ParkingSpaceNotExistsException
        when:
        parkingMeterService.check(new CheckParkingSpaceRequest(1L))
        then:
        thrown ParkingSpaceNotExistsException
    }

    def "should throw UnavailableParkingSpaceException because parkingSpace is not free"() {
        given:
        def tariffType = "REGULAR"
        def parking = EntitiesStubFactory.parkingWithAllTariffs()
        and:
        parkingSpaceRepositoryMock.findById(_ as Long) >> Optional.of(parking.parkingSpaces.find { it -> !it.isFree() })
        when:
        parkingMeterService.start(new StartParkingMeterRequest(1l, "registration", tariffType))
        then:
        thrown UnavailableParkingSpaceException
    }

    def "should successfully response from start method"() {
        given:
        def tariffType = "REGULAR"
        def parking = EntitiesStubFactory.parkingWithAllTariffs()
        def selectedTariff = parking.tariffsCollection.getTariff(tariffType)
        and:
        parkingSpaceRepositoryMock.findById(_ as Long) >> Optional.of(parking.parkingSpaces.find { it -> it.isFree() })
        and:
        chargeCalculatorMock.preCalculate(_ as VehicleRegistry, selectedTariff.precalculationsQuantity) >> new ArrayList<CalculatedChargeDto>()
        when:
        def response = parkingMeterService.start(new StartParkingMeterRequest(1l, "registration", tariffType))
        then:
        response != null
        response.startDateTime != null
    }

    def "should throw NoSuchVehicleParkedException because repository does not found on that parking Space currently parked vehicle"() {
        given:
        vehicleRegistryRepositoryMock.findByParkingSpaceIdAndIsParkedTrue(_ as Long) >> null
        when:
        parkingMeterService.stop(new StopParkingMeterRequest(1L))
        then:
        thrown NoSuchVehicleParkedException
    }

    def "should successfully response from stop method"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def vehicle = EntitiesStubFactory.vehicleRegistry(
                parking,
                EntitiesStubFactory.regularTariff(parking)
        )
        and:
        vehicleRegistryRepositoryMock.findByParkingSpaceIdAndIsParkedTrue(_ as Long) >> vehicle
        and:
        chargeCalculatorMock.calculateAndSave(vehicle) >> new CalculatedCharge(vehicle,new Monetary(BigDecimal.ZERO, Currency.PLN), vehicle.selectedTariff, LocalDateTime.now())
        when:
        def response = parkingMeterService.stop(new StopParkingMeterRequest(1L))
        then:
        response ==  new StopParkingMeterResponse(vehicle.startDateTime, vehicle.endDateTime, BigDecimal.ZERO, vehicle.selectedTariff.getTariffCurrency().name())
    }

    def "should throw NoSuchVehicleParkedException because it was not found in repository during operator check of parking space"() {
        given:
        def parking = EntitiesStubFactory.parking()
        EntitiesStubFactory.regularTariffWithAllRules(parking)
        and:
        parkingSpaceRepositoryMock.findById(_ as Long) >> Optional.of(parking.parkingSpaces.find { it -> it.isOccupied() })
        and:
        vehicleRegistryRepositoryMock.findByParkingSpaceIdAndIsParkedTrue(_ as Long) >> null
        when:
        parkingMeterService.check(new CheckParkingSpaceRequest(1L))
        then:
        thrown NoSuchVehicleParkedException
    }

    def "should response only with status to operator check of parking space"() {
        given:
        def parking = EntitiesStubFactory.parking()
        EntitiesStubFactory.regularTariffWithAllRules(parking)
        and:
        parkingSpaceRepositoryMock.findById(_ as Long) >> Optional.of(parking.parkingSpaces.find { it -> it.isFree() })
        when:
        def response = parkingMeterService.check(new CheckParkingSpaceRequest(1L))
        then:
        response == new CheckParkingSpaceResponse(ParkingSpaceStatus.FREE.name(), null, null)
    }

    def "should fully response to operator check of parking space"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, EntitiesStubFactory.regularTariff(parking))
        EntitiesStubFactory.regularTariffWithAllRules(parking)
        and:
        parkingSpaceRepositoryMock.findById(_ as Long) >> Optional.of(parking.parkingSpaces.find { it -> it.isOccupied() })
        and:
        vehicleRegistryRepositoryMock.findByParkingSpaceIdAndIsParkedTrue(_ as Long) >> vehicle
        when:
        def response = parkingMeterService.check(new CheckParkingSpaceRequest(1L))
        then:
        response == new CheckParkingSpaceResponse(ParkingSpaceStatus.OCCUPIED.name(), vehicle.registration, vehicle.startDateTime)
    }

    def "should find vehicle and response to operator check of vehicle"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, EntitiesStubFactory.regularTariff(parking))
        and:
        vehicleRegistryRepositoryMock.findByRegistrationAndIsParkedTrue(vehicle.registration) >> vehicle
        when:
        def response = parkingMeterService.check(new CheckVehicleRequest(vehicle.registration))
        then:
        response == new CheckVehicleResponse(vehicle.registration, true, vehicle.startDateTime, vehicle.parkingSpace.id)
    }

    def "should throw NoSuchVehicleParkedException to operator check of vehicle because vehicle is not parked on any parking space"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, EntitiesStubFactory.regularTariff(parking))
        and:
        vehicleRegistryRepositoryMock.findByRegistrationAndIsParkedTrue(vehicle.registration) >> null
        when:
        def response = parkingMeterService.check(new CheckVehicleRequest(vehicle.registration))
        then:
        response == new CheckVehicleResponse(vehicle.registration, false, null, null)
    }
}

package parkandrest.parkingmanagement.core.parkingmeter


import spock.lang.Specification
import parkandrest.parkingmanagement.api.tariff.TarifficationRuleIsMissingException
import parkandrest.parkingmanagement.core.parking.account.CalculatedCharge
import parkandrest.parkingmanagement.core.parking.account.CalculatedChargeRepository
import parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory

import java.math.RoundingMode

class ChargeCalculatorSpec extends Specification {

    def mockedRepository = Mock(CalculatedChargeRepository.class)
    def testedService = new ChargeCalculator(mockedRepository)

    def "should precalculate price for regular drivers vehicle with all rules present"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, tariff)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.recursiveRegularTarifficationRule(tariff),
                EntitiesStubFactory.secondRegularTarifficationRule(tariff),
        )
        when:
        def calculations = testedService.preCalculate(vehicle, 6)
        then:
        calculations.size() == 6
        calculations.sum { it.charge } == new BigDecimal(120).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should precalculate price for regular drivers vehicle with FIRST and SECOND rules present"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, tariff)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.secondRegularTarifficationRule(tariff),
        )
        when:
        def calculations = testedService.preCalculate(vehicle, 4)
        then:
        calculations.size() == 4
        calculations.sum { it.charge } == new BigDecimal(16).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should precalculate price for regular drivers vehicle with only FIRST rule present"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, tariff)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff)
        )
        when:
        def calculations = testedService.preCalculate(vehicle, 4)
        then:
        calculations.size() == 4
        calculations.sum { it.charge } == new BigDecimal(10).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should precalculate price for regular drivers vehicle with only SECOND rule not present"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, tariff)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.recursiveRegularTarifficationRule(tariff)
        )
        when:
        def calculations = testedService.preCalculate(vehicle, 6)
        then:
        calculations.size() == 6
        calculations.sum { it.charge } == new BigDecimal(234).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should end precalculate regular drivers vehicle with error because no rules are present"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, tariff)
        when:
        testedService.preCalculate(vehicle, 4)
        then:
        thrown TarifficationRuleIsMissingException
    }

    def "should calculate and save charge for VIP drivers vehicle (tariff has all rules)"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.vipTariff(parking)
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, tariff)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.secondVipTarifficationRule(tariff),
                EntitiesStubFactory.recursiveVipTarifficationRule(tariff)
        )
        and:
        mockedRepository.save(_ as CalculatedCharge) >> new CalculatedCharge()
        and:
        vehicle.leaveParking()
        when:
        def result = testedService.calculateAndSave(vehicle)
        then:
        result != null
    }

    def "should calculate and save charge for regular drivers vehicle (tariff does not have second rule)"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        def vehicle = EntitiesStubFactory.vehicleRegistry(parking, tariff)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.recursiveRegularTarifficationRule(tariff)
        )
        and:
        mockedRepository.save(_ as CalculatedCharge) >> new CalculatedCharge()
        and:
        vehicle.leaveParking()
        when:
        def result = testedService.calculateAndSave(vehicle)
        then:
        result != null
    }
}

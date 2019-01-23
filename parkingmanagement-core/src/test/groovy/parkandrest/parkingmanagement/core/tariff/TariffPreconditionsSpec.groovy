package parkandrest.parkingmanagement.core.tariff


import spock.lang.Specification
import parkandrest.parkingmanagement.api.tariff.DifferentParkingOnTariffsException
import parkandrest.parkingmanagement.api.tariff.DifferentTariffsTypesException
import parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory

class TariffPreconditionsSpec extends Specification {

    private def preconditions = new TariffPreconditions()

    def "should not throw anything because result is mergeable"() {
        given:
        def parking = EntitiesStubFactory.parkingWithAllTariffs()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        when:
        preconditions.checkIfMergeable(tariff, parking.tariffsCollection.getTariff("REGULAR"))
        then:
        noExceptionThrown()
    }

    def "should throw DifferentParkingOnTariffsException because parking on tariffs are different"() {
        given:
        def parking = EntitiesStubFactory.parkingWithAllTariffs()
        def tariff = EntitiesStubFactory.regularTariff(EntitiesStubFactory.parking())
        when:
        preconditions.checkIfMergeable(tariff, parking.tariffsCollection.getTariff("REGULAR"))
        then:
        thrown DifferentParkingOnTariffsException
    }

    def "should throw DifferentTariffsTypesException because types of tariffs are different"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def regularTariff = EntitiesStubFactory.regularTariff(parking)
        def vipTariff = EntitiesStubFactory.vipTariff(parking)
        when:
        preconditions.checkIfMergeable(regularTariff, vipTariff)
        then:
        thrown DifferentTariffsTypesException
    }
}

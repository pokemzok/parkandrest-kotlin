package parkandrest.parkingmanagement.core.parking

import com.google.common.collect.Sets
import spock.lang.Specification
import parkandrest.parkingmanagement.api.monetary.CurrencyMismatchException
import parkandrest.parkingmanagement.api.tariff.DifferentParkingOnTariffsException
import parkandrest.parkingmanagement.api.tariff.NoSuchTariffException
import parkandrest.parkingmanagement.api.tariff.NoTariffPresentException
import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory
import parkandrest.parkingmanagement.core.stubfactory.MonetaryStubFactory
import parkandrest.parkingmanagement.core.tariff.Period
import parkandrest.parkingmanagement.core.tariff.Tariff
import parkandrest.parkingmanagement.core.tariff.TariffType

class TariffsCollectionSpec extends Specification {

    def "should successfully add tariff "() {
        given:
        def parking = EntitiesStubFactory.parking()
        def collection = new TariffsCollection(
                Sets.newHashSet(
                       EntitiesStubFactory.regularTariff(parking)
                )
        )
        def tarrif = EntitiesStubFactory.vipTariff(parking)
        when:
        collection.addTariff(tarrif)
        then:
        collection.getTariffs().size() == 2
    }

    def "should successfully add tariff to empty collection and than add next tariff"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def collection = new TariffsCollection(
                Sets.newHashSet()
        )
        def tarrif = EntitiesStubFactory.vipTariff(parking)
        when:
        collection.addTariff(tarrif)
        then:
        collection.getTariffs().size() == 1
        when:
        collection.addTariff(EntitiesStubFactory.regularTariff(parking))
        then:
        collection.getTariffs().size() ==2
    }

    def "should successfully merge tariffs "() {
        given:
        def parking = EntitiesStubFactory.parking()
        def collection = new TariffsCollection(
                Sets.newHashSet(
                        EntitiesStubFactory.regularTariff(parking)
                )
        )
        def tarrif = EntitiesStubFactory.regularTariff(parking)
        when:
        collection.addTariff(tarrif)
        then:
        collection.getTariffs().size() == 1
    }

    def "should throw DifferentParkingOnTariffsException because added tariff has different parking"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def collection = new TariffsCollection(
                Sets.newHashSet(
                        EntitiesStubFactory.regularTariff(parking)
                )
        )
        and:
        def parking2 = EntitiesStubFactory.parking()
        def tarrif = EntitiesStubFactory.regularTariff(parking2)
        when:
        collection.addTariff(tarrif)
        then:
        thrown DifferentParkingOnTariffsException
    }

    def "should throw CurrencyMismatchException because new added tariff has incorrect Currency"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def collection = new TariffsCollection(
                Sets.newHashSet(
                        EntitiesStubFactory.regularTariff(parking)
                )
        )
        and:
        def tariff = new Tariff(
                parking,
                Period.WEEK,
                MonetaryStubFactory.ZERO_EU,
                new HashSet(),
                10L,
                TariffType.VIP
        )
        when:
        collection.addTariff(tariff)
        then:
        thrown CurrencyMismatchException
    }

    def "should succesfully get tariff from collection"(){
        given:
        def parking = EntitiesStubFactory.parkingWithAllTariffs()
        when:
        def tariff = parking.tariffsCollection.getTariff("VIP")
        then:
        tariff.tariffType == TariffType.VIP
    }

    def "should throw IllegalArgumentException because there is no enum value of this type"() {
        given:
        def parking = EntitiesStubFactory.parkingWithAllTariffs()
        when:
        parking.tariffsCollection.getTariff("TYPE_WHICH_NOT_EXISTS")
        then:
        thrown IllegalArgumentException
    }

    def "should throw NoSuchTariffException because there is no tariff in collection of this type"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        and:
        parking.tariffsCollection.addTariff(tariff)
        when:
        parking.tariffsCollection.getTariff("VIP")
        then:
        thrown NoSuchTariffException
    }

    def "should get currency from collection"() {
        given:
        def parking = EntitiesStubFactory.parkingWithAllTariffs()
        when:
        def currency = parking.tariffsCollection.collectionCurrency
        then:
        currency == Currency.PLN
    }

    def "should throw NoTariffPresentException because collection is empty"() {
        given:
        def collection = new TariffsCollection(Sets.newHashSet())
        when:
        collection.collectionCurrency
        then:
        thrown NoTariffPresentException
    }
}

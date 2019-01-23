package parkandrest.parkingmanagement.core.parking.account

import com.google.common.collect.Sets
import spock.lang.Specification
import parkandrest.parkingmanagement.api.monetary.CurrencyMismatchException
import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.stubfactory.MonetaryStubFactory

import java.math.RoundingMode

import static parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory.calculatedCharge

class CalculatedChargesCollectionSpec extends Specification {

    def "should throw CurrencyMismatchException because currency in collection does not match"() {
        given:
            def chargesCollection = new CalculatedChargesCollection(
                    Sets.newHashSet(
                            calculatedCharge(MonetaryStubFactory.ONE_USD),
                            calculatedCharge(MonetaryStubFactory.ONE_EU)
                    )
            )
        when:
            chargesCollection.sum(Currency.PLN)
        then:
            thrown CurrencyMismatchException
        when:
            chargesCollection.sum(Currency.USD)
        then:
            thrown CurrencyMismatchException
    }

    def "should sum correctly all charges"(){
        given:
        def chargesCollection = new CalculatedChargesCollection(
                Sets.newHashSet(
                        calculatedCharge(MonetaryStubFactory.ONE_EU),
                        calculatedCharge(new Monetary(new BigDecimal(2.22), Currency.EU)),
                        calculatedCharge(new Monetary(new BigDecimal(5.66), Currency.EU))
                )
        )
        when:
            Monetary sum = chargesCollection.sum(Currency.EU)
        then:
            def result = sum.roundToMoneyPrecision()
            result.amount == new BigDecimal(8.88).setScale(2, RoundingMode.HALF_UP)
            result.currency == Currency.EU
    }
}

package parkandrest.parkingmanagement.core.parking.account

import com.google.common.collect.Sets
import spock.lang.Specification
import parkandrest.parkingmanagement.api.monetary.CurrencyMismatchException
import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory
import parkandrest.parkingmanagement.core.stubfactory.MonetaryStubFactory

import java.math.RoundingMode

class PaymentsCollectionSpec extends Specification {

    def "should throw CurrencyMismatchException because currency in collection does not match"() {
        given:
        def paymentsCollection = new PaymentsCollection(
                Sets.newHashSet(
                        EntitiesStubFactory.payment(MonetaryStubFactory.ONE_USD, EntitiesStubFactory.calculatedCharge(MonetaryStubFactory.ONE_USD)),
                        EntitiesStubFactory.payment(MonetaryStubFactory.ONE_EU, EntitiesStubFactory.calculatedCharge(MonetaryStubFactory.ONE_EU))
                )
        )
        when:
        paymentsCollection.sum(Currency.PLN)
        then:
        thrown CurrencyMismatchException
        when:
        paymentsCollection.sum(Currency.USD)
        then:
        thrown CurrencyMismatchException
    }

    def "should sum correctly all payments"(){
        given:
        def secondPaymentMonetaryAmount = new Monetary(new BigDecimal(2.22), Currency.EU)
        def thirdPaymentMonetaryAmount = new Monetary(new BigDecimal(5.66), Currency.EU)
        def paymentsCollection = new PaymentsCollection(
                Sets.newHashSet(
                        EntitiesStubFactory.payment(MonetaryStubFactory.ONE_EU, EntitiesStubFactory.calculatedCharge(MonetaryStubFactory.ONE_EU)),
                        EntitiesStubFactory.payment(secondPaymentMonetaryAmount, EntitiesStubFactory.calculatedCharge(secondPaymentMonetaryAmount)),
                        EntitiesStubFactory.payment(thirdPaymentMonetaryAmount, EntitiesStubFactory.calculatedCharge(thirdPaymentMonetaryAmount))
                )
        )
        when:
        Monetary sum = paymentsCollection.sum(Currency.EU)
        then:
        def result = sum.roundToMoneyPrecision()
        result.amount == new BigDecimal(8.88).setScale(2, RoundingMode.HALF_UP)
        result.currency == Currency.EU
    }
}

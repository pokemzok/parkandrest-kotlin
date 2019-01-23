package parkandrest.parkingmanagement.core.monetary


import spock.lang.Specification
import parkandrest.parkingmanagement.api.monetary.CurrencyMismatchException
import parkandrest.parkingmanagement.core.stubfactory.MonetaryStubFactory

class MonetaryPreconditionsSpec extends Specification {

    private def preconditions = new MonetaryPreconditions()

    def "should throw nothing because Monetaries currencies are same"(){
        when:
        preconditions.checkMonetariesCurrencies(MonetaryStubFactory.ZERO_PLN, MonetaryStubFactory.ZERO_PLN)
        then:
        noExceptionThrown()
    }

    def "should throw CurrencyMismatchException because Monetaries currencies are different"(){
        when:
        preconditions.checkMonetariesCurrencies(MonetaryStubFactory.ZERO_EU, MonetaryStubFactory.ZERO_PLN)
        then:
        CurrencyMismatchException ex = thrown()
        ex.exceptionObjects.size() == 2
        ex.exceptionObjects == [Currency.EU, Currency.PLN]
    }
}

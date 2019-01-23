package parkandrest.parkingmanagement.core.stubfactory

import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.monetary.Monetary

class MonetaryStubFactory {
    static final ONE_USD = new Monetary(BigDecimal.ONE, Currency.USD)
    static final ONE_EU = new Monetary(BigDecimal.ONE, Currency.EU)
    static final ZERO_EU = new Monetary(BigDecimal.ZERO, Currency.EU)
    static final ZERO_PLN = new Monetary(BigDecimal.ZERO, Currency.PLN)
    static final ONE_PLN = new Monetary(BigDecimal.ONE, Currency.PLN)
}

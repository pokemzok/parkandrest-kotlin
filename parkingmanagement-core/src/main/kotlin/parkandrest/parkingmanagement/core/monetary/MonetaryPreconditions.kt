package parkandrest.parkingmanagement.core.monetary

import parkandrest.parkingmanagement.api.monetary.CurrencyMismatchException

internal object MonetaryPreconditions {

    /**
     * Checks two monetaries currencies.
     * Throws CurrencyMismatchException if this currencies does not match.
     * Throws NullPointerException with specified message if arguments or null or have some null key params.
     * @param monetary1 Monetary
     * @param monetary2 Monetary
     */
    fun checkMonetariesCurrencies(monetary1: Monetary, monetary2: Monetary) {
        if (monetary1.currency != monetary2.currency) {
            throw CurrencyMismatchException(arrayOf(monetary1.currency, monetary2.currency), "Compared currencies does not match: " + monetary1.currency
                    + " , " + monetary2.currency)
        }
    }
}

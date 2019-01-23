package parkandrest.parkingmanagement.core.parking.account

import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.monetary.Monetary

internal class PaymentsCollection (private val payments: Collection<Payment>) {

    /**
     * Sums all payments in collection.
     * All payments in collection should have tarrifsCurrency, otherwise CurrencyMismatchException will be thrown
     * @param tarrifsCurrency - expected collection currency
     * @return sum result in Monetary
     */
    fun sum(tarrifsCurrency: Currency): Monetary {
        return payments
                .map{ it.amount }
                .fold(Monetary.zero(tarrifsCurrency)) { obj, monetary -> obj.add(monetary) }
                .roundToMoneyPrecision()
    }

}

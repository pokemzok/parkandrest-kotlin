package parkandrest.parkingmanagement.core.parking.account

import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.monetary.Monetary

internal class CalculatedChargesCollection(private val calculatedCharges: Collection<CalculatedCharge>) {

    /**
     * Sums all charges in collection.
     * All charges in collection should have tarrifsCurrency, otherwise CurrencyMismatchException will be thrown.
     * @param tarrifsCurrency - expected collection currency
     * @return sum result in Monetary
     */
     fun sum(tarrifsCurrency: Currency): Monetary {
        return calculatedCharges
                .map{ it.charge }
                .fold(Monetary.zero(tarrifsCurrency)){ obj, monetary -> obj.add(monetary) }
                .roundToMoneyPrecision()
    }

}

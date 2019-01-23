package parkandrest.parkingmanagement.core.parking

import org.slf4j.LoggerFactory
import parkandrest.parkingmanagement.api.monetary.CurrencyMismatchException
import parkandrest.parkingmanagement.api.tariff.NoSuchTariffException
import parkandrest.parkingmanagement.api.tariff.NoTariffPresentException
import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.extensions.orElseThrow
import parkandrest.parkingmanagement.core.tariff.Tariff
import parkandrest.parkingmanagement.core.tariff.TariffType

class TariffsCollection(val tariffs: MutableCollection<Tariff>) {

    companion object {
        private val log = LoggerFactory.getLogger(TariffsCollection::class.java)
    }

    /**
     * All elements in collection must have same Currency, therefore method search for any tariff and returns it's currency.
     * @return Currency
     * @throws NoTariffPresentException - thrown if no tariff is present in collection
     */
    val collectionCurrency: Currency
        get() = tariffs
                .firstOrNull()
                .orElseThrow(NoTariffPresentException())
                .tariffCurrency

    /**
     * Add's tariffs to collection if it's not already there, if it is will merge new tariff into old one.
     * Currency should match with collection currency, otherwise CurrencyMismatchException will be thrown.
     * Tariff type of added tariff should not be null otherwise IllegalArgumentException will be thrown.
     * If tariff should be merged it must pass TariffPreconditions validation otherwise Exceptions will be thrown.
     * @param tariffs array of Tariff
     */
    fun addTariff(vararg tariffs: Tariff) {
        for (tariff in tariffs) {
            checkCurrency(tariff)
            val result: Tariff? = this.tariffs.find { it -> it.tariffType == tariff.tariffType }
            if (result == null) this.tariffs.add(tariff) else result.merge(tariff)
        }
    }

    private fun checkCurrency(tariff: Tariff) {
        try {
            if (collectionCurrency != tariff.tariffCurrency) {
                throw CurrencyMismatchException(arrayOf(tariff.tariffCurrency, collectionCurrency), "Failed to add Tariff to parking because currency does not match Collection currency")
            }
        } catch (e: NoTariffPresentException) {
            log.info("No tariff in collection, therefore there are no currency to compare. " + tariff.tariffCurrency + " will become a new collection currency ")
        }

    }

    /**
     * Get's Tariff by type. If there is none throws NoSuchTariffException.
     * @param tariffType String with type
     * @return Tariff
     */
    fun getTariff(tariffType: String): Tariff {
        return tariffs
                .find { tariff -> tariff.tariffType == TariffType.valueOf(tariffType) }
                .orElseThrow(NoSuchTariffException())
    }
}

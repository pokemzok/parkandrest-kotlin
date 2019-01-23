package parkandrest.parkingmanagement.core.monetary

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class Monetary(
        @Column(name = "AMOUNT", nullable = false)
        val amount: BigDecimal,

        @Column(name = "CURRENCY", nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        val currency: Currency
) {


    /**
     * @param times - multiplier
     * @return new Monetary multiplied by argument times
     */
    fun multiply(times: BigDecimal): Monetary {
        return Monetary(
                amount.multiply(times),
                currency
        )
    }

    /**
     * Sums two monetaries.
     * Throws CurrencyMismatchException if Monetaries currencies does not match.
     * Throws NullPointerException with specified message if one of monetaries or it's key params are null.
     * @param monetary - Monetary object to sum
     * @return sum of this and monetary argument
     */
    fun add(monetary: Monetary): Monetary {
        MonetaryPreconditions.checkMonetariesCurrencies(this, monetary)
        return Monetary(this.amount.add(monetary.amount), this.currency)
    }

    /**
     *
     * @return new Monetary rounded half up with precision 2
     */
    fun roundToMoneyPrecision(): Monetary {
        val precision = 2
        return Monetary(
                this.amount.setScale(precision, BigDecimal.ROUND_HALF_UP),
                this.currency
        )
    }

    companion object {
        /**
         * @param currency - currency value of Monetary
         * @return Monetary with zero value and selected currency
         */
        fun zero(currency: Currency): Monetary {
            return Monetary(BigDecimal.ZERO, currency)
        }
    }
}

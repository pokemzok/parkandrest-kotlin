package parkandrest.parkingmanagement.core.tariff

import java.util.*

/**
 * Context for Formula calculations
 */
class CalculationContext : Comparable<CalculationContext> {

    val id: UUID = UUID.randomUUID()

    override fun compareTo(other: CalculationContext): Int {
        return id.compareTo(other.id)
    }


}

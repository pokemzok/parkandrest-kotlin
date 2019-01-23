package parkandrest.parkingmanagement.core.tariff


import parkandrest.parkingmanagement.api.tariff.IllegalOrderException
import java.util.*

/**
 * Defines an Order for a TarifficationRule Collection
 * Only first rule must be required
 * Ready for any new not required rule which must come before RECURSIVE_NEXT
 */
enum class RuleOrder(val order: Long, val isRuleRequired: Boolean) {
    FIRST(1L, true),
    SECOND(2L, false),
    RECURSIVE_NEXT(3L, false);

    companion object {
        internal fun ofOrder(order: Long): RuleOrder {
            return if (order > RECURSIVE_NEXT.order) {
                RECURSIVE_NEXT
            } else Arrays.stream(RuleOrder.values())
                    .filter { ruleOrder -> ruleOrder.order == order }
                    .findFirst()
                    .orElseThrow { IllegalOrderException(arrayOf(order), "No rule found for the order") }
        }
    }
}

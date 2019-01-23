package parkandrest.parkingmanagement.core.tariff

import parkandrest.parkingmanagement.api.tariff.TarifficationRuleIsMissingException
import parkandrest.parkingmanagement.core.extensions.orElseThrow

/**
 * Manages tarifficationRules set
 */
class TarifficationRulesCollection(val tarifficationRules: MutableCollection<TarifficationRule>) {

    private val recurrenceRule: TarifficationRule?
        get() = tarifficationRules.find { rule -> rule.ruleOrder == RuleOrder.RECURSIVE_NEXT }

    /**
     * adds tariffication rule if it not exists (searching by RuleOrder) otherwise merge it with one in collection
     * @param tarifficationRules an Array of TarifficationRule
     */
    fun addTarifficationRule(vararg tarifficationRules: TarifficationRule) {
        for (tarifficationRule in tarifficationRules) {
            val rule = this.tarifficationRules.find { it -> it.ruleOrder == tarifficationRule.ruleOrder }
            if (rule != null) rule.merge(tarifficationRule) else this.tarifficationRules.add(tarifficationRule)
        }
    }

    /**
     * @param ruleOrder order of the rule to retrieve, (examples 1 -> FIRST, 2 -> SECOND, 3-> RECURSIVE_NEXT)
     * @return chosen Rule if it was present. If it was not it either throws TarifficationRuleIsMissingException (if it was a required rule)
     * or get's last available rule from tarifficationRules set.
     */
    fun getRuleByOrder(ruleOrder: Long): TarifficationRule {
        val order = RuleOrder.ofOrder(ruleOrder)
        return tarifficationRules
                .find { rule -> rule.ruleOrder == order }
                ?: if (order.isRuleRequired) throw TarifficationRuleIsMissingException(arrayOf(ruleOrder), "Tarrification rule is missing but is required")
                else recurrenceRule ?: this.lastAvailableRule()
    }

    private fun lastAvailableRule(): TarifficationRule {
        return tarifficationRules.sortedWith(Comparator { o1, o2 -> (o2.ruleOrder.order - o1.ruleOrder.order).toInt() })
                .firstOrNull()
                .orElseThrow(TarifficationRuleIsMissingException(arrayOf(1L), "Tarrification rule is missing but is required"))
    }

    private fun firstRule(): TarifficationRule {
        return tarifficationRules
                .find { rule -> rule.ruleOrder == RuleOrder.FIRST }
                .orElseThrow(TarifficationRuleIsMissingException(arrayOf(1L), "Tarrification rule is missing but is required"))
    }
}


package parkandrest.parkingmanagement.core.tariff

import javax.persistence.*

@Entity
@Table(name = "TARIFFICATION_RULE")
class TarifficationRule(
        @ManyToOne
        @JoinColumn(name = "TARIFF_ID", nullable = false)
        internal var tariff: Tariff,

        @Column(name = "RULE_ORDER", nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        internal var ruleOrder: RuleOrder,

        @Column(name = "RAW_FORMULA", nullable = false)
        internal var rawFormula: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set

    @Transient
    private var formula: Formula? = null

    internal fun merge(tarifficationRule: TarifficationRule) {
        this.rawFormula = tarifficationRule.rawFormula
        this.ruleOrder = tarifficationRule.ruleOrder
        this.tariff = tarifficationRule.tariff
    }

    fun formula(): Formula {
        if (formula == null) {
            formula = Formula(rawFormula, tariff.startAmount)
        }
        return formula!!
    }
}

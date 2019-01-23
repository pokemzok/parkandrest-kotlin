package parkandrest.parkingmanagement.core.tariff

import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.parking.Parking
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TARIFF")
class Tariff(
        @ManyToOne
        @JoinColumn(name = "PARKING_ID", nullable = false)
        internal var parking: Parking,

        @Column(name = "PERIOD", nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        internal var period: Period,

        internal var startAmount: Monetary,

        @OneToMany(mappedBy = "tariff", cascade = [CascadeType.ALL])
        private var tarifficationRules: MutableSet<TarifficationRule> = mutableSetOf(),

        @Column(name = "PRECALCULATIONS_QUANTITY", nullable = false, length = 1)
        internal var precalculationsQuantity: Long,

        @Column(name = "TYPE", nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        val tariffType: TariffType
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set

    @Transient
    var tarifficationRulesCollection: TarifficationRulesCollection = TarifficationRulesCollection(tarifficationRules)
        get() = TarifficationRulesCollection(tarifficationRules) //TODO: ask on stack overflow is null check here i possible
        private set

    val tariffCurrency: Currency
        get() = startAmount.currency


    fun merge(tariff: Tariff) {
        TariffPreconditions.checkIfMergeable(this, tariff)
        startAmount = tariff.startAmount
        parking = tariff.parking
        period = tariff.period
        tarifficationRules = HashSet(tariff.tarifficationRules)
        precalculationsQuantity = tariff.precalculationsQuantity
        tarifficationRulesCollection = TarifficationRulesCollection(tarifficationRules)
    }

}



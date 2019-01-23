package parkandrest.parkingmanagement.core.parking.account

import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.tariff.Tariff
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "PAYMENT")
class Payment(
        @ManyToOne
        @JoinColumn(name = "PARKING_ACCOUNT_ID", nullable = false)
        val parkingAccount: ParkingAccount,

        val amount: Monetary,

        @Column(name = "PAYMENT_DATE_TIME", nullable = false)
        val paymentDateTime: LocalDateTime,

        @ManyToOne
        @JoinColumn(name = "SELECTED_TARIFF_ID", nullable = false)
        val selectedTariff: Tariff,

        @ManyToOne
        @JoinColumn(name = "CALCULATED_CHARGE_ID", nullable = false)
        val calculatedCharge: CalculatedCharge
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set
}

package parkandrest.parkingmanagement.core.parking.account

import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.tariff.Tariff
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistry
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "CALCULATED_CHARGE")
class CalculatedCharge(
        @OneToOne
        @JoinColumn(name = "VEHICLE_REGISTRY_ID", nullable = false)
        val vehicleRegistry: VehicleRegistry,

        val charge: Monetary,

        @ManyToOne
        @JoinColumn(name = "SELECTED_TARIFF_ID", nullable = false)
        val selectedTariff: Tariff,

        @Column(name = "CALCULATION_DATE_TIME", nullable = false)
        val calculationDateTime: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set
}

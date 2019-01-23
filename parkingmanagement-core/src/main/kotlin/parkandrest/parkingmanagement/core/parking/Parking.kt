package parkandrest.parkingmanagement.core.parking

import parkandrest.parkingmanagement.core.parking.account.ParkingAccount
import parkandrest.parkingmanagement.core.parking.space.ParkingSpace
import parkandrest.parkingmanagement.core.tariff.Tariff
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "PARKING")
class Parking(
        @Column(name = "NAME", nullable = false)
        val name: String,

        @Column(name = "ADDRESS")
        val address: String,

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "parking")
        private val tariffs: MutableSet<Tariff> = mutableSetOf(),

        @OneToMany(mappedBy = "parking", cascade = [CascadeType.ALL])
        val parkingSpaces: MutableSet<ParkingSpace> = mutableSetOf(),

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "PARKING_ACCOUNT_ID", nullable = false)
        val parkingAccount: ParkingAccount
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set

    @Transient
    var tariffsCollection: TariffsCollection = TariffsCollection(tariffs)
        get() = TariffsCollection(tariffs) //TODO: ask on stack oveflow is null check here i possible
        private set

    fun addParkingSpace(vararg parkingSpaces: ParkingSpace) {
        this.parkingSpaces.addAll(Arrays.asList(*parkingSpaces))
    }

    fun addParkingSpace(parkingSpaces: Collection<ParkingSpace>) {
        this.parkingSpaces.addAll(parkingSpaces)
    }

}

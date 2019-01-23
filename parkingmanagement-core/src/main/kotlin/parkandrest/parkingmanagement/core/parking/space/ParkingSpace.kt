package parkandrest.parkingmanagement.core.parking.space

import parkandrest.parkingmanagement.core.parking.Parking
import javax.persistence.*

@Entity
@Table(name = "PARKING_SPACE")
class ParkingSpace(
        @Column(name = "STATUS", nullable = false, length = 50)
        @Enumerated(EnumType.STRING)
        internal var status: ParkingSpaceStatus,

        @ManyToOne
        @JoinColumn(name = "PARKING_ID", nullable = false)
        val parking: Parking
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set

    fun isFree(): Boolean = ParkingSpaceStatus.FREE == status

    fun isOccupied(): Boolean = ParkingSpaceStatus.OCCUPIED == status

    fun free() {
        status = ParkingSpaceStatus.FREE
    }

    fun occupy() {
        status = ParkingSpaceStatus.OCCUPIED
    }

    fun maintain() {
        status = ParkingSpaceStatus.MAINTENANCE
    }
}

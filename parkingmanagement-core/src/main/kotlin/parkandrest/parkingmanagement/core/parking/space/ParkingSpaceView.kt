package parkandrest.parkingmanagement.core.parking.space

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "PARKING_SPACE_VIEW")
class ParkingSpaceView(
        @Id
        @Column(name = "PARKING_SPACE_ID")
        val id: Long,

        @Column(name = "STATUS")
        val status: String,

        @Column(name = "REGISTRATION")
        val registration: String,

        @Column(name = "OCCUPIED_FROM", nullable = false)
        val occupiedFrom: LocalDateTime
)
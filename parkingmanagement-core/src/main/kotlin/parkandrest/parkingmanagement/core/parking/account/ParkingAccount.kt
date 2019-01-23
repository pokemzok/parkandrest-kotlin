package parkandrest.parkingmanagement.core.parking.account

import javax.persistence.*

@Entity
@Table(name = "PARKING_ACCOUNT")
class ParkingAccount(
        @Column(name = "IBAN", length = 34, nullable = false)
        val iban: String
) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        var id: Long? = null
                private set
}

package parkandrest.parkingmanagement.core.vehicle

import parkandrest.parkingmanagement.api.parking.space.UnavailableParkingSpaceException
import parkandrest.parkingmanagement.core.parking.space.ParkingSpace
import parkandrest.parkingmanagement.core.tariff.Tariff
import parkandrest.timemanagement.SystemTimeManager
import java.time.Duration
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "VEHICLE_REGISTRY")
class VehicleRegistry(parkingSpace: ParkingSpace, selectedTariff: Tariff, registration: String, startDateTime: LocalDateTime) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null
        private set

    @ManyToOne
    @JoinColumn(name = "PARKING_SPACE_ID", nullable = false)
    var parkingSpace: ParkingSpace
        private set

    @ManyToOne
    @JoinColumn(name = "SELECTED_TARIFF_ID")
    var selectedTariff: Tariff
        private set

    @Column(name = "REGISTRATION", nullable = false, length = 15)
    var registration: String
        private set

    @Column(name = "START_DATE_TIME", nullable = false)
    var startDateTime: LocalDateTime
        private set

    @Column(name = "END_DATE_TIME", nullable = false)
    var endDateTime: LocalDateTime
        private set

    @Column(name = "IS_PARKED", nullable = false)
    var isParked: Boolean
        private set

    val parkingDuration: Duration
        get() = Duration.between(startDateTime, endDateTime)

    init {
        if (parkingSpace.isFree()) {
            parkingSpace.occupy()
            this.parkingSpace = parkingSpace
            this.selectedTariff = selectedTariff
            this.registration = registration
            this.startDateTime = startDateTime
            this.endDateTime = SystemTimeManager.maxSystemDateTime()
            this.isParked = true
        } else {
            throw UnavailableParkingSpaceException()
        }
    }

    fun leaveParking() {
        this.parkingSpace.free()
        this.endDateTime = SystemTimeManager.systemDateTime
        this.isParked = false
    }
}

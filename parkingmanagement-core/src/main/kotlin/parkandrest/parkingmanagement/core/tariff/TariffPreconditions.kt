package parkandrest.parkingmanagement.core.tariff

import parkandrest.parkingmanagement.api.tariff.DifferentParkingOnTariffsException
import parkandrest.parkingmanagement.api.tariff.DifferentTariffsTypesException

internal object TariffPreconditions {

    /**
     * Checks if two tariffs could be merged.
     * Would throw DifferentParkingOnTariffsException or DifferentTariffsTypesException if tariffs are non mergeable.
     * Would throw NullPointerException if any of method arguments are null or tariff types are null or related with tariffs parking are null.
     * @param tariff1 Tariff
     * @param tariff2 Tariff
     */
    fun checkIfMergeable(tariff1: Tariff, tariff2: Tariff) {
        checkTariffsType(tariff1, tariff2)
        checkTariffsParking(tariff1, tariff2)
    }

    private fun checkTariffsParking(tariff1: Tariff, tariff2: Tariff) {
        if (tariff1.parking != tariff2.parking) {
            throw DifferentParkingOnTariffsException(arrayOf(tariff1.parking.name, tariff2.parking.name), "Parking on tariffs are different. Tariff 1 parking id is " + tariff1.parking.id
                    + ", tariff 2 parking id is " + tariff2.parking.id)
        }
    }

    private fun checkTariffsType(tariff1: Tariff, tariff2: Tariff) {
        if (tariff1.tariffType != tariff2.tariffType) {
            throw DifferentTariffsTypesException(arrayOf(tariff1.tariffType, tariff2.tariffType), "Tariffs types are different. Tariff 1 type: " + tariff1.tariffType
                    + ", tariff 2 type: " + tariff2.tariffType)
        }
    }
}

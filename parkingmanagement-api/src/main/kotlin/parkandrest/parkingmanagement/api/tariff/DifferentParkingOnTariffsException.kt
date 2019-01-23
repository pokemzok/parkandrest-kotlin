package parkandrest.parkingmanagement.api.tariff

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode

class DifferentParkingOnTariffsException(exceptionObjects: Array<Any>, exceptionMessage: String) : BusinessException(CODE, exceptionObjects, exceptionMessage) {
    companion object {
        private val CODE = BusinessExceptionCode.DIFFERENT_PARKING_ON_TARIFFS.name
    }
}

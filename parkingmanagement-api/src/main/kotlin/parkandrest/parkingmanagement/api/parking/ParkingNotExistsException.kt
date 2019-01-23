package parkandrest.parkingmanagement.api.parking

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode


class ParkingNotExistsException : BusinessException(CODE, arrayOf(), "") {
    companion object {
        private val CODE = BusinessExceptionCode.PARKING_NOT_EXISTS.name
    }
}

package parkandrest.parkingmanagement.api.parking.space

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode


class UnavailableParkingSpaceException : BusinessException(CODE, arrayOf(), "") {
    companion object {
        private val CODE = BusinessExceptionCode.PARKING_SPACE_UNAVAILABLE.name
    }
}

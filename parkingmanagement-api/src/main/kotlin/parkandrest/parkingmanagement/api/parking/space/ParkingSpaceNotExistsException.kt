package parkandrest.parkingmanagement.api.parking.space

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode


class ParkingSpaceNotExistsException : BusinessException(CODE, arrayOf(), "") {
    companion object {
        private val CODE = BusinessExceptionCode.PARKING_SPACE_NOT_EXISTS.name
    }
}

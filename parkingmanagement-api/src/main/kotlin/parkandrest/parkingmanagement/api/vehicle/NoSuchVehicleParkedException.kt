package parkandrest.parkingmanagement.api.vehicle

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode

class NoSuchVehicleParkedException : BusinessException(CODE, arrayOf(), "") {
    companion object {
        private val CODE = BusinessExceptionCode.NO_SUCH_VEHICLE_PARKED.name
    }
}

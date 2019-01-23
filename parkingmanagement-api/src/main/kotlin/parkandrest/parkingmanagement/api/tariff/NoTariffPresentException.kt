package parkandrest.parkingmanagement.api.tariff

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode

class NoTariffPresentException : BusinessException(CODE, arrayOf(), "") {
    companion object {
        private val CODE = BusinessExceptionCode.NO_TARIFF_PRESENT.name
    }
}

package parkandrest.parkingmanagement.api.tariff

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode

class NoSuchTariffException : BusinessException(CODE, arrayOf(), "") {
    companion object {
        private val CODE = BusinessExceptionCode.NO_SUCH_TARIFF.name
    }
}

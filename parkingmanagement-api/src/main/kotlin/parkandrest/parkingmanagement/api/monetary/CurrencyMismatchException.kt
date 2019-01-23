package parkandrest.parkingmanagement.api.monetary

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode

class CurrencyMismatchException(exceptionObjects: Array<Any>,  exceptionMessage: String): BusinessException(CODE, exceptionObjects, exceptionMessage){
    companion object {
        private val CODE = BusinessExceptionCode.CURRENCY_MISMATCH.name
    }
}
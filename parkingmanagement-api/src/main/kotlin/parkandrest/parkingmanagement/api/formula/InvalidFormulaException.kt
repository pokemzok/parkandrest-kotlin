package parkandrest.parkingmanagement.api.formula

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode

class InvalidFormulaException(exceptionObjects: Array<Any>, message: String?) : BusinessException(CODE, exceptionObjects, message) {
    companion object {
        private val CODE = BusinessExceptionCode.INVALID_FORMULA.name
    }
}
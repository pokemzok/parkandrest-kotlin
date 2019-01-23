package parkandrest.security.api.usermanagement

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.security.api.usermanagement.businessexception.BusinessExceptionCode

class NoUserFoundException(exceptionObjects: Array<Any>, exceptionMessage: String) : BusinessException(CODE, exceptionObjects, exceptionMessage) {
    companion object {
        private val CODE = BusinessExceptionCode.NO_USER_FOUND.name
    }
}

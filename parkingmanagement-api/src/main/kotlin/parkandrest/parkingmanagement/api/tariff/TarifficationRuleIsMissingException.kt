package parkandrest.parkingmanagement.api.tariff

import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.api.businessexception.BusinessExceptionCode

class TarifficationRuleIsMissingException(exceptionObjects: Array<Any>, exceptionMessage: String) : BusinessException(CODE, exceptionObjects, exceptionMessage) {
    companion object {
        private val CODE = BusinessExceptionCode.TARIFFICATION_RULE_IS_MISSING.name
    }
}

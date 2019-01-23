package parkandrest.exception.api.exceptionparent

open class BusinessException(val code: String? = null, val exceptionObjects: Array<Any>? = null, exceptionMessage: String? =null) : RuntimeException(exceptionMessage)


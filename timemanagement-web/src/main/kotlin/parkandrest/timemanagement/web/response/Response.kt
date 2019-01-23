package parkandrest.timemanagement.web.response


import parkandrest.exception.api.exceptionparent.BusinessException

class Response<T>{

    var content: T? = null
        private set

    var isSuccess: Boolean = false
        private set

    var serverError: BusinessException? = null
        private set

    constructor(content: T) {
        this.content = content
        this.isSuccess = true
    }

    constructor(error: Throwable) {
        this.isSuccess = false
        val code = "UNEXPECTED_EXCEPTION"
        this.serverError = BusinessException(code, arrayOf(), error.message)
    }


}

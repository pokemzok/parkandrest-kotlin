package parkandrest.parkingmanagement.web.controller.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import parkandrest.exception.api.exceptionparent.BusinessException
import parkandrest.parkingmanagement.web.response.Response

@ControllerAdvice
@ResponseBody
class ExceptionsHandler : ResponseEntityExceptionHandler() {

    companion object {
        private val log = LoggerFactory.getLogger(ExceptionsHandler::class.java)
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(BusinessException::class)
    fun handleException(ex: BusinessException, request: WebRequest): Response<Void> {
        log.info("Expected exception with business code {} occurred", ex.code)
        return Response(ex)
    }

}

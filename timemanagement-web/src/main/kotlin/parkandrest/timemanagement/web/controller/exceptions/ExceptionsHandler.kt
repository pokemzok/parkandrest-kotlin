package parkandrest.timemanagement.web.controller.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import parkandrest.timemanagement.web.response.Response
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
@ResponseBody
class ExceptionsHandler : ResponseEntityExceptionHandler() {

    companion object {
        private val log = LoggerFactory.getLogger(ExceptionsHandler::class.java)
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun unexpectedException(req: HttpServletRequest, exception: Throwable): Response<Void> {
        log.error("!UNEXPECTED ERROR OCCURRED!")
        log.error(exception.message, exception)
        log.error("-------------------------------------------------------")
        return Response(exception)
    }

}

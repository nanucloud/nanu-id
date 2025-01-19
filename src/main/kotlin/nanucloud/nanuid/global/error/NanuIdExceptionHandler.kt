package nanucloud.nanuid.global.error

import nanucloud.nanuid.global.error.exception.NanuIdException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class NanuIdExceptionHandler {
    @ExceptionHandler(NanuIdException::class)
    fun customExceptionHandling(e: NanuIdException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        return ResponseEntity(
            ErrorResponse(
                status = errorCode.httpStatus,
                message = errorCode.message
            ),
            HttpStatus.valueOf(errorCode.httpStatus)
        )
    }

    @ExceptionHandler(BindException::class)
    fun bindExceptionHandling(e: BindException): ResponseEntity<Map<String, String>> {
        val errorList = mutableMapOf<String, String>()
        for (error in e.fieldErrors) {
            errorList[error.field] = error.defaultMessage ?: "Unknown Error Occurred"
        }
        return ResponseEntity(errorList, HttpStatus.BAD_REQUEST)
    }
}
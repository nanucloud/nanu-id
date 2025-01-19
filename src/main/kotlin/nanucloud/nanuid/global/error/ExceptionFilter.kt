package nanucloud.nanuid.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException
import org.apache.coyote.BadRequestException
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException

class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(ExceptionFilter::class.java)

    @Throws(IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: NanuIdException) {
            sendErrorMessage(response, e.errorCode)
        } catch (e: Exception) {
            logger.error("Exception caught", e)
            sendErrorMessage(response, ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    @Throws(IOException::class)
    private fun sendErrorMessage(response: HttpServletResponse, errorCode: ErrorCode) {
        val errorResponse = ErrorResponse(
            status = errorCode.httpStatus,
            message = errorCode.message
        )
        val errorResponseJson = objectMapper.writeValueAsString(errorResponse)

        response.status = errorCode.httpStatus
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(errorResponseJson)
    }
}

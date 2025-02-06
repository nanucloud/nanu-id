package nanucloud.nanuid.global.base

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class IpUtils {
    fun getClientIp(request: HttpServletRequest): String {
        var clientIp = request.getHeader("X-Forwarded-For")
        clientIp = if (clientIp == null || clientIp.isEmpty()) {
            request.remoteAddr
        } else {
            clientIp.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }
        return clientIp
    }
}
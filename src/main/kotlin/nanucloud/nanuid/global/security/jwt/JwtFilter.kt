package nanucloud.nanuid.global.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import kotlin.jvm.Throws

@RequiredArgsConstructor
@Component
class JwtFilter (
    private val jwtTokenProvider: JwtProvider
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearer = jwtTokenProvider.resolveToken(request)

        if (bearer != null && jwtTokenProvider.validToken(bearer)) {
            val authentication: Authentication = jwtTokenProvider.authentication(bearer)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
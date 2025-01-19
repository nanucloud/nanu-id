package nanucloud.nanuid.global.security.jwt

import lombok.RequiredArgsConstructor
import nanucloud.nanuid.domain.auth.domain.DeviceType
import nanucloud.nanuid.domain.auth.domain.RefreshToken
import nanucloud.nanuid.domain.auth.repository.RefreshTokenRepository
import nanucloud.nanuid.global.security.auth.AuthDetailsService
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.stereotype.Component
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest
import nanucloud.nanuid.global.security.jwt.exception.ExpiredTokenException
import nanucloud.nanuid.global.security.jwt.exception.InvalidTokenException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken


@RequiredArgsConstructor
@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    companion object {
        const val ACCESS_KEY = "access_token"
        const val REFRESH_KEY = "refresh_token"
    }

    fun generateToken(accountId: String, applicationId: String, deviceType: DeviceType): TokenResponse {
        val accessToken = generateToken(accountId, ACCESS_KEY, jwtProperties.accessExp)
        val refreshToken = generateToken(accountId, REFRESH_KEY, jwtProperties.refreshExp)

        val authTime = LocalDateTime.now()

        refreshTokenRepository.save(
            RefreshToken(
                accountId = accountId,
                applicationId = applicationId,
                deviceType = deviceType,
                authTime = authTime
            )
        )

        return TokenResponse(accessToken, refreshToken)
    }

    fun validToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.jwtSecret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun generateToken(id: String, type: String, exp: Long): String {
        return Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.jwtSecret)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer = request.getHeader(jwtProperties.header)
        return if (bearer != null && bearer.startsWith(jwtProperties.prefix) && bearer.length > jwtProperties.prefix.length + 1) {
            bearer.substring(jwtProperties.prefix.length + 1)
        } else null
    }

    fun authentication(token: String): Authentication {
        val body = getJws(token).body
        if (!isNotRefreshToken(token)) throw InvalidTokenException

        val userDetails: UserDetails = getDetails(body)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun isNotRefreshToken(token: String): Boolean {
        return REFRESH_KEY != getJws(token).header["typ"].toString()
    }

    fun validateRefreshToken(accountId: String, refreshToken: String): Boolean {
        val refreshTokenUuid = try {
            UUID.fromString(refreshToken)
        } catch (e: IllegalArgumentException) {
            return false
        }

        val storedRefreshToken = refreshTokenRepository.findById(refreshTokenUuid).orElse(null)
        return storedRefreshToken != null && storedRefreshToken.refreshTokenId == refreshTokenUuid
    }

    private fun getJws(token: String): Jws<Claims> {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.jwtSecret).parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException
        } catch (e: Exception) {
            throw InvalidTokenException
        }
    }

    private fun getDetails(body: Claims): UserDetails {
        return authDetailsService.loadUserByUsername(body.subject)
    }
}
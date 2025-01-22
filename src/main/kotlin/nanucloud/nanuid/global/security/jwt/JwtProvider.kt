package nanucloud.nanuid.global.security.jwt

import lombok.RequiredArgsConstructor
import nanucloud.nanuid.domain.auth.domain.DeviceType
import nanucloud.nanuid.domain.auth.domain.RefreshToken
import nanucloud.nanuid.domain.auth.repository.RefreshTokenRepository
import nanucloud.nanuid.global.security.auth.AuthDetailsService
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.stereotype.Component
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import nanucloud.nanuid.global.security.jwt.exception.ExpiredTokenException
import nanucloud.nanuid.global.security.jwt.exception.InvalidTokenException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.nio.charset.StandardCharsets


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

    @Transactional
    fun generateToken(accountId: String, applicationId: String, deviceType: DeviceType): TokenResponse {
        val accessToken = generateJwtToken(accountId, ACCESS_KEY, jwtProperties.accessExp)
        val refreshToken = generateJwtToken(accountId, REFRESH_KEY, jwtProperties.refreshExp)
        val authTime = LocalDateTime.now()

        refreshTokenRepository.save(
            RefreshToken(
                refreshTokenId = UUID.randomUUID().toString(),
                refreshToken = accessToken,
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
            val signingKey = Keys.hmacShaKeyFor(
                jwtProperties.secret.toByteArray(StandardCharsets.UTF_8)
            )

            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun generateJwtToken(id: String, type: String, exp: Long): String {
        val signingKey = Keys.hmacShaKeyFor(
            jwtProperties.secret.toByteArray(StandardCharsets.UTF_8)
        )
        return Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .signWith(signingKey, SignatureAlgorithm.HS256)
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
        val storedRefreshToken = refreshTokenRepository.findById(refreshToken).orElse(null)
        return storedRefreshToken != null && storedRefreshToken.refreshTokenId == refreshToken
    }

    private fun getJws(token: String): Jws<Claims> {
        return try {
            val signingKey = Keys.hmacShaKeyFor(
                jwtProperties.secret.toByteArray(StandardCharsets.UTF_8)
            )

            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
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
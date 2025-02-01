package nanucloud.nanuid.global.security.jwt

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import nanucloud.nanuid.domain.auth.domain.DeviceType
import nanucloud.nanuid.domain.auth.domain.RefreshToken
import nanucloud.nanuid.domain.auth.persistence.repository.RefreshTokenRepository
import nanucloud.nanuid.global.security.auth.AuthDetailsService
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.stereotype.Component
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.auth.mapper.RefreshTokenMapper
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
    private val refreshTokenRepository: RefreshTokenRepository,
    private val refreshTokenMapper: RefreshTokenMapper
) {
    @Autowired
    private lateinit var request: HttpServletRequest

    companion object {
        const val ACCESS_KEY = "access_token"
        const val REFRESH_KEY = "refresh_token"
    }

    @Transactional
    fun generateToken(userId: String, applicationId: String, deviceType: DeviceType, authScope:  Set<AuthScope>): TokenResponse {
        val accessToken = generateJwtToken(userId, ACCESS_KEY, jwtProperties.accessExp, authScope)
        val refreshToken = generateJwtToken(userId, REFRESH_KEY, jwtProperties.refreshExp, authScope)
        val authTime = LocalDateTime.now()

        val refreshTokenEntity = RefreshToken(
            refreshToken = refreshToken,
            userId = userId,
            applicationId = applicationId,
            deviceType = deviceType,
            authTime = authTime
        )
        refreshTokenRepository.save(refreshTokenMapper.toEntity(refreshTokenEntity))

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

            val authScopes: Set<AuthScope> = getAuthScopeFromToken(token)
            request.setAttribute("authScopes", authScopes)

            true
        } catch (e: Exception) {
            false
        }
    }

    private fun calculateAuthScopeBitmask(scopes: Set<AuthScope>): Int {
        return scopes.fold(0) { acc, scope -> acc or scope.bit }
    }

    fun generateJwtToken(id: String, type: String, exp: Long,authScopes: Set<AuthScope>): String {
        val signingKey = Keys.hmacShaKeyFor(
            jwtProperties.secret.toByteArray(StandardCharsets.UTF_8)
        )
        val bitmaskAuthScope = calculateAuthScopeBitmask(authScopes)
        return Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .claim("authScope", bitmaskAuthScope)
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

    fun authentication(token:String): Authentication {
        val body = getJws(token).body
        if (!isNotRefreshToken(token)) throw InvalidTokenException

        val userDetails: UserDetails = getDetails(body)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun isNotRefreshToken(token: String): Boolean {
        return REFRESH_KEY != getJws(token).header["typ"].toString()
    }

    fun validateRefreshToken(refreshToken: String): Boolean {
        val storedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
        return storedRefreshToken.isPresent && storedRefreshToken.get().refreshToken == refreshToken
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

    fun getAuthScopeFromToken(token: String): Set<AuthScope> {
        val body = getJws(token).body
        val bitmask = body["authScope"] as? Int ?: 0
        return AuthScope.values().filter { scope -> (bitmask and scope.bit) != 0 }.toSet()
    }

    fun getuserIdFromToken(token: String): String {
        val body = getJws(token).body
        return body.subject
    }

}
package nanucloud.nanuid.domain.user.service

import nanucloud.nanuid.global.security.jwt.JwtProperties
import nanucloud.nanuid.global.security.jwt.JwtProvider
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import nanucloud.nanuid.global.security.jwt.exception.ExpiredTokenException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserReissueService(
    private val jwtProvider:JwtProvider
) {
    @Autowired
    lateinit var jwtProperties: JwtProperties

    fun execute(refreshToken: String): TokenResponse {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw ExpiredTokenException
        }
        val accountId = jwtProvider.getuserIdFromToken(refreshToken)
        val authScopes = jwtProvider.getAuthScopeFromToken(refreshToken)
        val accessToken = jwtProvider.generateJwtToken(accountId, JwtProvider.ACCESS_KEY, jwtProperties.accessExp, authScopes)

        return TokenResponse(accessToken,refreshToken)
    }
}
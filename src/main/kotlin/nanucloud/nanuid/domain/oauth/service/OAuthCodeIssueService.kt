package nanucloud.nanuid.domain.oauth.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.application.persistence.repository.ApplicationJpaRepository
import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.auth.domain.DeviceType
import nanucloud.nanuid.domain.oauth.exception.InvalidClientSecretException
import nanucloud.nanuid.domain.oauth.exception.InvalidCodeException
import nanucloud.nanuid.domain.oauth.mapper.OAuthClientAuthCodeMapper
import nanucloud.nanuid.domain.oauth.persistence.repository.OAuthClientAuthCodeRedisRepository
import nanucloud.nanuid.domain.oauth.presentation.dto.request.OAuthTokenRequest
import nanucloud.nanuid.domain.user.persistence.repository.UserJpaRepository
import nanucloud.nanuid.global.security.jwt.JwtProvider
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class OAuthCodeIssueService(
    private val oAuthClientAuthCodeRedisRepository: OAuthClientAuthCodeRedisRepository,
    private val oAuthClientAuthCodeMapper: OAuthClientAuthCodeMapper,
    private val applicationJpaRepository: ApplicationJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val jwtProvider: JwtProvider
) {
    @Transactional
    fun execute(oAuthTokenRequest: OAuthTokenRequest): TokenResponse {
        val authCodeEntity = oAuthClientAuthCodeRedisRepository.findById(UUID.fromString(oAuthTokenRequest.authCode))
            .orElseThrow { throw InvalidCodeException }

        val authCodeDomain = oAuthClientAuthCodeMapper.toDomain(authCodeEntity)

        val user = userJpaRepository.findById(UUID.fromString(authCodeDomain.userId))
            .orElseThrow { throw InvalidCodeException }

        val application = applicationJpaRepository.findById(UUID.fromString(authCodeDomain.applicationId))
            .orElseThrow { throw InvalidCodeException }

        if (application.applicationSecret != oAuthTokenRequest.applicationSecret) {
            throw InvalidClientSecretException
        }

        val authScopes = AuthScope.fromBits(authCodeDomain.authScope)

        oAuthClientAuthCodeRedisRepository.deleteById(UUID.fromString(oAuthTokenRequest.authCode))

        return jwtProvider.generateToken(
            authCodeDomain.userId,
            authCodeDomain.applicationId,
            application.name,
            authCodeDomain.deviceType,
            authScopes
        )
    }
}
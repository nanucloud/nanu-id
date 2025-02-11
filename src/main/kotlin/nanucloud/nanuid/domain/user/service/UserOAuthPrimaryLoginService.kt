package nanucloud.nanuid.domain.user.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.application.persistence.repository.ApplicationJpaRepository
import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.auth.domain.DeviceType
import nanucloud.nanuid.domain.oauth.domain.OAuthClientAuthCode
import nanucloud.nanuid.domain.oauth.mapper.OAuthClientAuthCodeMapper
import nanucloud.nanuid.domain.oauth.persistence.repository.OAuthClientAuthCodeRedisRepository
import nanucloud.nanuid.domain.permission.persistence.repository.PermissionJpaRepository
import nanucloud.nanuid.domain.user.client.RecaptchaClient
import nanucloud.nanuid.domain.user.exception.*
import nanucloud.nanuid.domain.user.persistence.repository.UserJpaRepository
import nanucloud.nanuid.domain.user.presentation.dto.request.UserOAuthLoginRequest
import nanucloud.nanuid.domain.user.presentation.dto.response.UserOAuthLoginResponse
import nanucloud.nanuid.global.security.jwt.JwtProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserOAuthPrimaryLoginService(
    private val userJpaRepository: UserJpaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val oAuthClientAuthCodeMapper: OAuthClientAuthCodeMapper,
    private val recaptchaClient: RecaptchaClient,
    private val applicationJpaRepository: ApplicationJpaRepository,
    private val permissionJpaRepository: PermissionJpaRepository,
    private val oAuthClientAuthCodeRedisRepository: OAuthClientAuthCodeRedisRepository,
    @Value("\${recaptcha.secret-key}") private val recaptchaSecretKey: String
) {
    @Transactional
    fun execute(userOAuthLoginRequest: UserOAuthLoginRequest): UserOAuthLoginResponse {
        validateRecaptchaToken(userOAuthLoginRequest.recaptchaToken)

        val user = userJpaRepository.findByEmail(userOAuthLoginRequest.email)
            .orElseThrow { throw UserNotFoundException }

        if (!user.isEnabled) {
            throw UserAccountSuspendedException
        }
        if (user.isAccountLocked) {
            throw UserAccountLockedException
        }

        if (!passwordEncoder.matches(userOAuthLoginRequest.password, user.password) ||
            !passwordEncoder.matches(userOAuthLoginRequest.pin, user.pin)
        ) {
            throw UserNotFoundException
        }

        val application = applicationJpaRepository.findById(UUID.fromString(userOAuthLoginRequest.applicationId))
            .orElseThrow { throw ApplicationNotFoundException }

        //if (!application.redirectUris.contains(userOAuthLoginRequest.applicationRedirectUri)) {
        //    throw InvalidRedirectUriException
        //}

        val authCode = UUID.randomUUID()

        val oAuthClientAuthCode = OAuthClientAuthCode(
            authCodeId = authCode,
            userId = user.id.toString(),
            applicationId = userOAuthLoginRequest.applicationId,
            authScope = userOAuthLoginRequest.authScope,
            deviceType = userOAuthLoginRequest.deviceType ?: DeviceType.WEB_UNKNOWN
        )

        oAuthClientAuthCodeRedisRepository.save(
            oAuthClientAuthCodeMapper.toEntity(oAuthClientAuthCode)
        )

        return UserOAuthLoginResponse(
            code = authCode.toString()
        )
    }

    private fun validateRecaptchaToken(recaptchaToken: String) {
        if (recaptchaToken.isBlank()) {
            throw InvalidRecaptchaTokenException
        }
        try {
            val response = recaptchaClient.verifyRecaptcha(recaptchaSecretKey, recaptchaToken)
            if (!response.success) {
                throw InvalidRecaptchaTokenException
            }
        } catch (e: Exception) {
            throw InvalidRecaptchaTokenException
        }
    }
}
package nanucloud.nanuid.domain.user.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.auth.domain.DeviceType
import nanucloud.nanuid.domain.user.client.RecaptchaClient
import nanucloud.nanuid.domain.user.presentation.dto.request.UserLoginRequest
import nanucloud.nanuid.domain.user.exception.InvalidRecaptchaTokenException
import nanucloud.nanuid.domain.user.exception.UserAccountLockedException
import nanucloud.nanuid.domain.user.exception.UserAccountSuspendedException
import nanucloud.nanuid.domain.user.exception.UserNotFoundException
import nanucloud.nanuid.domain.user.persistence.repository.UserRepository
import nanucloud.nanuid.global.security.jwt.JwtProvider
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserPrimaryLoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val recaptchaClient: RecaptchaClient,
    @Value("\${recaptcha.secret-key}") private val recaptchaSecretKey: String
) {

    @Transactional
    fun execute(userLoginRequest: UserLoginRequest): TokenResponse {
        validateRecaptchaToken(userLoginRequest.recaptchaToken)

        val user = userRepository.findByEmail(userLoginRequest.email).orElseThrow {
            throw UserNotFoundException
        }

        if (!user.isEnabled) {
            throw UserAccountSuspendedException
        }

        if (user.isAccountLocked) {
            throw UserAccountLockedException
        }

        if (!passwordEncoder.matches(
                userLoginRequest.password,
                user.password) || !passwordEncoder.matches(
                userLoginRequest.pin,
                user.pin
            )
        ) {
            throw UserNotFoundException
        }

        val requestDeviceType = userLoginRequest.deviceType ?: DeviceType.WEB_UNKNOWN
        return jwtProvider.generateToken(user.id.toString(),"00000000-0000-0000-0000-000000000001","NANU_ID DashBoard Web Service", requestDeviceType, setOf(AuthScope.FULL_ACCESS))
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
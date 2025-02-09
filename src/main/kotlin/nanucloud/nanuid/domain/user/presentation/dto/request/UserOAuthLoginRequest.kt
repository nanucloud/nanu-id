package nanucloud.nanuid.domain.user.presentation.dto.request

import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.auth.domain.DeviceType

data class UserOAuthLoginRequest(
    val email: String,
    val password: String,
    val pin: String,
    val recaptchaToken: String,
    val deviceType: DeviceType?,
    val applicationId: String,
    val applicationRedirectUri: String,
    val authScope: Int
)

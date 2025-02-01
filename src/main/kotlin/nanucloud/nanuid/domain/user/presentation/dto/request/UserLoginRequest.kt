package nanucloud.nanuid.domain.user.presentation.dto.request

import nanucloud.nanuid.domain.auth.domain.DeviceType

data class UserLoginRequest(
    val email: String,
    val password: String,
    val recaptchaToken : String,
    val pin : String,
    val deviceType: DeviceType?
)

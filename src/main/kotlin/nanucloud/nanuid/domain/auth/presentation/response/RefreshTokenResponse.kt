package nanucloud.nanuid.domain.auth.presentation.response

import nanucloud.nanuid.domain.auth.domain.DeviceType
import java.time.LocalDateTime

data class RefreshTokenResponse(
    val refreshTokenId: String,
    val applicationId: String,
    val applicationName: String,
    val deviceType: DeviceType,
    val authTime: LocalDateTime,
    val ip: String
)
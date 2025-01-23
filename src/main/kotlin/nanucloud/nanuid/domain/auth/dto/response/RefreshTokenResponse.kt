package nanucloud.nanuid.domain.auth.dto.response

import nanucloud.nanuid.domain.auth.domain.DeviceType
import java.time.LocalDateTime

data class RefreshTokenResponse(
    val refreshTokenId: String,
    val applicationId: String,
    val deviceType: DeviceType,
    val authTime: LocalDateTime,
)
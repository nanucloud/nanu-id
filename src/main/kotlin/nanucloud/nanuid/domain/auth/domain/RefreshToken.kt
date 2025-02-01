package nanucloud.nanuid.domain.auth.domain

import java.time.LocalDateTime
import java.util.UUID

data class RefreshToken(
    val refreshTokenId: UUID? = null,
    val refreshToken: String,
    val userId: String,
    val applicationId: String,
    val deviceType: DeviceType,
    val authTime: LocalDateTime
)

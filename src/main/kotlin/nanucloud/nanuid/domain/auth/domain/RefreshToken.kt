package nanucloud.nanuid.domain.auth.domain

import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_refresh_token")
data class RefreshToken(
    @Id
    @Column(name = "refresh_token_id", nullable = false, unique = true)
    val refreshTokenId: String,

    @Column(name = "refresh_token", nullable = false, unique = true)
    val refreshToken: String,

    @Column(name = "account_id", nullable = false)
    val accountId: String,

    @Column(name = "application_id", nullable = false)
    val applicationId: String,

    @Column(name = "device_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val deviceType: DeviceType,

    @Column(name = "auth_time", nullable = false)
    val authTime: LocalDateTime,

    @Version
    @Column(name = "version")
    val version: Long = 0
)

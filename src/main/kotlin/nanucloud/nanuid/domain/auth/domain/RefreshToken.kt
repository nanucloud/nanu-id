package nanucloud.nanuid.domain.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_refresh_token")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "refresh_token_id", nullable = false, unique = true)
    val refreshTokenId: UUID = UUID.randomUUID(),

    @Column(name = "account_id", nullable = false)
    val accountId: String,

    @Column(name = "application_id", nullable = false)
    val applicationId: String,

    @Column(name = "device_type", nullable = false)
    val deviceType: DeviceType,

    @Column(name = "auth_time", nullable = false)
    val authTime: LocalDateTime
)

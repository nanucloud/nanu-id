package nanucloud.nanuid.domain.auth.entity

import jakarta.persistence.*
import nanucloud.nanuid.domain.auth.domain.DeviceType
import java.time.LocalDateTime
import nanucloud.nanuid.global.security.base.BaseUUIDEntity
import java.util.*

@Entity
@Table(name = "tbl_refresh_token")
class RefreshTokenJpaEntity(
    id: UUID?,

    @Column(name = "refresh_token", nullable = false, unique = true)
    val refreshToken: String,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "application_id", nullable = false)
    val applicationId: String,

    @Column(name = "device_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val deviceType: DeviceType,

    @Column(name = "auth_time", nullable = false)
    val authTime: LocalDateTime
) : BaseUUIDEntity(id)

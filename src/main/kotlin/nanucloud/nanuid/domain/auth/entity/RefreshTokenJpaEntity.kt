package nanucloud.nanuid.domain.auth.entity

import jakarta.persistence.*
import nanucloud.nanuid.domain.application.entity.ApplicationJpaEntity
import nanucloud.nanuid.domain.auth.domain.DeviceType
import nanucloud.nanuid.domain.user.entity.UserJpaEntity
import java.time.LocalDateTime
import nanucloud.nanuid.global.base.BaseUUIDEntity
import java.util.*

@Entity
@Table(name = "tbl_refresh_token")
class RefreshTokenJpaEntity(
    id: UUID?,

    @Column(name = "refresh_token", nullable = false, unique = true)
    val refreshToken: String,

    @JoinColumn(name = "application", nullable = false)
    val applicationId: String,

    @JoinColumn(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "device_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val deviceType: DeviceType,

    @Column(name = "auth_time", nullable = false)
    val authTime: LocalDateTime
) : BaseUUIDEntity(id)


package nanucloud.nanuid.domain.user.entity

import jakarta.persistence.*
import nanucloud.nanuid.domain.application.entity.ApplicationJpaEntity
import nanucloud.nanuid.domain.auth.entity.RefreshTokenJpaEntity
import nanucloud.nanuid.global.base.BaseUUIDEntity
import java.util.*

@Entity
@Table(name = "tbl_user")
class UserJpaEntity(
    id: UUID?,

    @Column(name = "device_token", nullable = false)
    val deviceToken: String?,

    @Column(name = "security_pin", nullable = false)
    val pin: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "birth_date", nullable = false)
    val birthDate: Date,

    @Column(name = "account_enabled", nullable = false)
    val isEnabled: Boolean,

    @Column(name = "account_locked", nullable = false)
    val isAccountLocked: Boolean,
) : BaseUUIDEntity(id)

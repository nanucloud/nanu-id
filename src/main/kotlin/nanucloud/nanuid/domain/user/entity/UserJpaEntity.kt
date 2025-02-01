package nanucloud.nanuid.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import nanucloud.nanuid.global.security.base.BaseUUIDEntity
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

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "birth_date", nullable = false)
    val birthDate: Date,

    @Column(name = "account_enabled", nullable = false)
    val isEnabled: Boolean,

    @Column(name = "account_locked", nullable = false)
    val isAccountLocked: Boolean
) : BaseUUIDEntity(id)

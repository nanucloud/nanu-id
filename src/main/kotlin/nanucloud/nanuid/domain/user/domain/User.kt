package nanucloud.nanuid.domain.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "tbl_user")
data class User(
    @Id
    @Column(name = "user_id", nullable = false)
    val userId: UUID = UUID.randomUUID(),

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
)

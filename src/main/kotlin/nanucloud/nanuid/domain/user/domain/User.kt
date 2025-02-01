package nanucloud.nanuid.domain.user.domain

import java.util.*

data class User(
    val userId: UUID? = null,
    val deviceToken: String?,
    val pin: String,
    val password: String,
    val name: String,
    val email: String,
    val birthDate: Date,
    val isEnabled: Boolean,
    val isAccountLocked: Boolean
)
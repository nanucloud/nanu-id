package nanucloud.nanuid.domain.user.dto.request

import java.util.*

data class UserRegisterRequest(
    val deviceToken: String,
    val pin: String,
    val password: String,
    val name: String,
    val email: String,
    val birthDate: Date
)

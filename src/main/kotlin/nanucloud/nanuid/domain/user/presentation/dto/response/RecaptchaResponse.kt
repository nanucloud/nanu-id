package nanucloud.nanuid.domain.user.presentation.dto.response

data class RecaptchaResponse(
    val success: Boolean,
    val challengeTs: String?,
    val hostname: String?,
    val errorCodes: List<String>?
)

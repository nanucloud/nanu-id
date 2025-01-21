package nanucloud.nanuid.global.security.jwt.dto

data class TokenResponse(
    val access_token: String,
    val refresh_token: String
)

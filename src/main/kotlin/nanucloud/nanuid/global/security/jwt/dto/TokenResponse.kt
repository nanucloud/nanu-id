package nanucloud.nanuid.global.security.jwt.dto

data class TokenResponse(
    private val accessToken: String,
    private val refreshToken: String
)

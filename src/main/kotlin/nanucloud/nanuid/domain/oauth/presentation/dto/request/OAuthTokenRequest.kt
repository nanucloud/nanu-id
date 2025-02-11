package nanucloud.nanuid.domain.oauth.presentation.dto.request

data class OAuthTokenRequest(
    val authCode: String,
    val applicationSecret: String
)

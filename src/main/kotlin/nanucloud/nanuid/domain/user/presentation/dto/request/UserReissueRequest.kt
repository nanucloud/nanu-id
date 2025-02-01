package nanucloud.nanuid.domain.user.presentation.dto.request

import nanucloud.nanuid.domain.auth.domain.RefreshToken

data class UserReissueRequest(
    val refreshToken: String
)

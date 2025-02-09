package nanucloud.nanuid.domain.oauth.domain

import nanucloud.nanuid.domain.auth.domain.AuthScope
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*

data class OAuthClientAuthCode(
    val authCodeId: UUID,
    val userId: String,
    val applicationId: String,
    val authScope: Int
)
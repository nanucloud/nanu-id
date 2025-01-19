package nanucloud.nanuid.domain.oauth.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*

@RedisHash("OAuthClientAuthCode")
data class OAuthClientAuthCode(
    @Id
    val authCodeId: UUID,

    @Indexed
    val userId: String
)
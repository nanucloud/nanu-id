package nanucloud.nanuid.domain.oauth.entity

import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.auth.domain.DeviceType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.UUID

@RedisHash("OAuthClientAuthCode")
class OAuthClientAuthCodeRedisEntity(
    @Id
    val authCodeId: UUID,

    @Indexed
    val userId: String,

    val applicationId : String,
    val authScope: Int,
    val deviceType: DeviceType
)
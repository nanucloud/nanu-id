package nanucloud.nanuid.domain.oauth.mapper

import nanucloud.nanuid.domain.oauth.domain.OAuthClientAuthCode
import nanucloud.nanuid.domain.oauth.entity.OAuthClientAuthCodeRedisEntity
import org.springframework.stereotype.Component

@Component
class OAuthClientAuthCodeMapper {
    fun toEntity(domain: OAuthClientAuthCode): OAuthClientAuthCodeRedisEntity {
        return domain.run {
            OAuthClientAuthCodeRedisEntity(
                authCodeId = authCodeId,
                userId = userId,
                applicationId = applicationId,
                authScope = authScope,
                deviceType = deviceType
            )
        }
    }

    fun toDomain(entity: OAuthClientAuthCodeRedisEntity): OAuthClientAuthCode {
        return entity.run {
            OAuthClientAuthCode(
                authCodeId = authCodeId,
                userId = userId,
                applicationId = applicationId,
                authScope = authScope,
                deviceType = deviceType
            )
        }
    }
}
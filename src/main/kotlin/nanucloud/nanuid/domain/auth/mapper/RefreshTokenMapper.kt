package nanucloud.nanuid.domain.auth.mapper

import nanucloud.nanuid.domain.auth.domain.RefreshToken
import nanucloud.nanuid.domain.auth.entity.RefreshTokenJpaEntity
import nanucloud.nanuid.global.base.CommonEntityMapper
import org.springframework.stereotype.Component

@Component
class RefreshTokenMapper : CommonEntityMapper<RefreshTokenJpaEntity, RefreshToken> {
    override fun toEntity(domain: RefreshToken): RefreshTokenJpaEntity {
        return domain.run {
            RefreshTokenJpaEntity(
                id = domain.refreshTokenId,
                refreshToken = domain.refreshToken,
                userId = domain.userId,
                applicationId = domain.applicationId,
                applicationName = domain.applicationName,
                deviceType = domain.deviceType,
                authTime = domain.authTime,
                ip = domain.ip
            )
        }
    }

    override fun toDomain(entity: RefreshTokenJpaEntity): RefreshToken {
        return entity.run {
            RefreshToken(
                refreshTokenId = entity.id,
                refreshToken = entity.refreshToken,
                userId = entity.userId,
                applicationId = entity.applicationId,
                applicationName = entity.applicationName,
                deviceType = entity.deviceType,
                authTime = entity.authTime,
                ip = entity.ip
            )
        }
    }
}

package nanucloud.nanuid.domain.oauth.persistence.repository

import nanucloud.nanuid.domain.oauth.entity.OAuthClientAuthCodeRedisEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OAuthClientAuthCodeRedisRepository : CrudRepository<OAuthClientAuthCodeRedisEntity, UUID> {
    fun findByUserId(userId: String): OAuthClientAuthCodeRedisEntity?
}
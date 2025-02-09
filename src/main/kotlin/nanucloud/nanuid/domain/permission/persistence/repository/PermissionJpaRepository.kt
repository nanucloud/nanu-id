package nanucloud.nanuid.domain.permission.persistence.repository

import nanucloud.nanuid.domain.oauth.domain.OAuthClientAuthCode
import nanucloud.nanuid.domain.permission.entity.PermissionJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PermissionJpaRepository : CrudRepository<PermissionJpaEntity, UUID> {
    fun findByUserId(userId: String): PermissionJpaEntity?
}
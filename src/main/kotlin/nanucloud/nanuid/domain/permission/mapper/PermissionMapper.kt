package nanucloud.nanuid.domain.permission.mapper

import nanucloud.nanuid.domain.permission.domain.Permission
import nanucloud.nanuid.domain.permission.entity.PermissionJpaEntity
import nanucloud.nanuid.global.base.CommonEntityMapper
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PermissionMapper : CommonEntityMapper<PermissionJpaEntity, Permission> {
    override fun toEntity(domain: Permission): PermissionJpaEntity {
        return domain.run {
            PermissionJpaEntity(
                id = domain.permissionId,
                userId = domain.userId,
                applicationId = domain.applicationId.toString()
            )
        }
    }

    override fun toDomain(entity: PermissionJpaEntity): Permission {
        return entity.run {
            Permission(
                permissionId = entity.id!!,
                userId = entity.userId,
                applicationId = UUID.fromString(entity.applicationId)
            )
        }
    }
}
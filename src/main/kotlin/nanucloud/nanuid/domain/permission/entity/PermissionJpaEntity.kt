package nanucloud.nanuid.domain.permission.entity

import jakarta.persistence.*
import nanucloud.nanuid.global.base.BaseUUIDEntity
import java.util.UUID

@Entity
@Table(name = "tbl_permission")
class PermissionJpaEntity(
    id: UUID?,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "application_id", nullable = false)
    val applicationId: String,

    @Version
    @Column(name = "version")
    val version: Long? = 0L
) : BaseUUIDEntity(id)
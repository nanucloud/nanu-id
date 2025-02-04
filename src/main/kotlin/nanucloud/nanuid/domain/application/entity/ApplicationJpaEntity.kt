package nanucloud.nanuid.domain.application.entity

import jakarta.persistence.*
import nanucloud.nanuid.domain.user.entity.UserJpaEntity
import nanucloud.nanuid.global.base.BaseUUIDEntity
import java.util.*

@Entity
@Table(name = "tbl_application")
class ApplicationJpaEntity(
    id: UUID,

    @Column(name = "owner_id", nullable = false, columnDefinition = "BINARY(16)")
    val ownerId: String,

    @Column(name = "name", nullable = false, length = 255)
    val name: String,

    @Column(name = "description", nullable = false, length = 255)
    val description: String,

    @Column(name = "is_public", nullable = false)
    val isPublic: Boolean,

    @Column(name = "application_secret", nullable = false, length = 255)
    val applicationSecret: String,

    @Version
    @Column(name = "version")
    val version: Long? = 0L
) : BaseUUIDEntity(id)

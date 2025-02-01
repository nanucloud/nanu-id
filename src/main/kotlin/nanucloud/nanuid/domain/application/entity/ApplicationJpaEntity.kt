package nanucloud.nanuid.domain.application.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import nanucloud.nanuid.global.security.base.BaseUUIDEntity
import java.util.*

@Entity
@Table(name = "tbl_application")
class ApplicationJpaEntity(
    id: UUID?,

    @Column(name = "owner", nullable = false, columnDefinition = "BINARY(16)")
    val ownerId: UUID,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "is_public")
    val isPublic: Boolean,

    @Column(name = "application_secret", length = 25)
    val applicationSecret: String
) : BaseUUIDEntity(id)

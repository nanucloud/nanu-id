package nanucloud.nanuid.domain.permission.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_permission")
data class Permission(
    @Id
    @Column(name = "permission_id")
    val permissionId: UUID,

    @Column(name = "user_id")
    val userId : String,

    @Column(name = "application_id")
    val applicationId: UUID
)

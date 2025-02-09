package nanucloud.nanuid.domain.permission.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

data class Permission(
    val permissionId: UUID? = null,
    val userId: String,
    val applicationId: UUID
)
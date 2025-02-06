package nanucloud.nanuid.domain.application.domain

import java.util.UUID

data class Application(
    val applicationId: UUID? = null,
    val ownerId: UUID,
    val name: String,
    val description: String,
    val isPublic: Boolean,
    val applicationSecret: String
)
package nanucloud.nanuid.domain.application.domain

import java.util.UUID

data class Application(
    val applicationId: UUID,
    val ownerId: String,
    val name: String,
    val description: String,
    val isPublic: Boolean,
    val applicationSecret: String
)
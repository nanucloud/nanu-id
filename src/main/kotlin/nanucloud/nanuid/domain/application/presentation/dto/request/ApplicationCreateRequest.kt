package nanucloud.nanuid.domain.application.presentation.dto.request

data class ApplicationCreateRequest(
    val name: String,
    val isPublic: Boolean,
    val description: String
)

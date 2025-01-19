package nanucloud.nanuid.domain.application.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "tbl_application")
data class Application(
    @Id
    @Column(name = "application_id", nullable = false)
    val applicationId: UUID,

    @Column(name = "owner", nullable = false)
    val owner: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "is_public")
    val isPublic: Boolean,

    @Column(name = "application_secret", length = 25)
    val applicationSecret: String,
)
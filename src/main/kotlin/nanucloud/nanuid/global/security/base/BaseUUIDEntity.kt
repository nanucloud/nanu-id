package nanucloud.nanuid.global.security.base

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.UuidGenerator
import java.util.*

@MappedSuperclass
abstract class BaseUUIDEntity(
    @Id
    @UuidGenerator
    @Column(
        columnDefinition = "BINARY(16)",
        nullable = true,
        updatable = false
    )
    val id: UUID?
)
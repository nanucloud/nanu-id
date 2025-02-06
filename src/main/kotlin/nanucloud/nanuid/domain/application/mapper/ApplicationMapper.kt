package nanucloud.nanuid.domain.application.mapper

import nanucloud.nanuid.domain.application.domain.Application
import nanucloud.nanuid.domain.application.entity.ApplicationJpaEntity
import nanucloud.nanuid.global.base.CommonEntityMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationMapper : CommonEntityMapper<ApplicationJpaEntity, Application> {
    override fun toEntity(domain: Application): ApplicationJpaEntity {
        return domain.run {
            ApplicationJpaEntity(
                id = domain.applicationId,
                ownerId = domain.ownerId.toString(),
                name = domain.name,
                description = domain.description,
                isPublic = domain.isPublic,
                applicationSecret = domain.applicationSecret
            )
        }
    }

    override fun toDomain(entity: ApplicationJpaEntity): Application {
        return entity.run {
            Application(
                applicationId = entity.id,
                ownerId = UUID.fromString(entity.ownerId),
                name = entity.name,
                description = entity.description,
                isPublic = entity.isPublic,
                applicationSecret = entity.applicationSecret
            )
        }
    }
}

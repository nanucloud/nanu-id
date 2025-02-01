package nanucloud.nanuid.domain.application.persistence.repository

import nanucloud.nanuid.domain.application.entity.ApplicationJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ApplicationRepository:CrudRepository<ApplicationJpaEntity,UUID>  {
}
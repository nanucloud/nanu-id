package nanucloud.nanuid.domain.application.persistence.repository

import nanucloud.nanuid.domain.application.entity.ApplicationJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ApplicationRepository:CrudRepository<ApplicationJpaEntity,UUID>  {
    fun findByName(name: String): Optional<ApplicationJpaEntity>
    fun findAllByOwnerId(ownerId: String): List<ApplicationJpaEntity>
}
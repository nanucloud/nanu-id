package nanucloud.nanuid.domain.user.persistence.repository

import nanucloud.nanuid.domain.user.entity.UserJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserJpaRepository:CrudRepository<UserJpaEntity,UUID>{
    fun findByEmail(email: String): Optional<UserJpaEntity>
}
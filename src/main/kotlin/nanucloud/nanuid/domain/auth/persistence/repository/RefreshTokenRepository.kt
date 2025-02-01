package nanucloud.nanuid.domain.auth.persistence.repository

import nanucloud.nanuid.domain.auth.entity.RefreshTokenJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RefreshTokenRepository:CrudRepository<RefreshTokenJpaEntity,String> {
    fun findByUserId(userId: String): List<RefreshTokenJpaEntity>
    fun findByRefreshToken(token: String): Optional<RefreshTokenJpaEntity>
}
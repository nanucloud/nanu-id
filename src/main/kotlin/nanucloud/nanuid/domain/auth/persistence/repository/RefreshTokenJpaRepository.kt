package nanucloud.nanuid.domain.auth.persistence.repository

import io.lettuce.core.dynamic.annotation.Param
import nanucloud.nanuid.domain.auth.entity.RefreshTokenJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RefreshTokenJpaRepository:CrudRepository<RefreshTokenJpaEntity,String> {
    fun findByRefreshToken(token: String): Optional<RefreshTokenJpaEntity>

    @Query("SELECT r FROM RefreshTokenJpaEntity r WHERE r.userId = :userId ORDER BY r.authTime DESC")
    fun findByUserId(@Param("userId") userId: String, pageable: Pageable): Page<RefreshTokenJpaEntity>

    fun deleteByUserId(userId: String)
}
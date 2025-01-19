package nanucloud.nanuid.domain.auth.repository

import nanucloud.nanuid.domain.auth.domain.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RefreshTokenRepository:CrudRepository<RefreshToken,UUID> {
}
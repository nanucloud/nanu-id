package nanucloud.nanuid.domain.auth.repository

import nanucloud.nanuid.domain.auth.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository:CrudRepository<RefreshToken,String> {
}
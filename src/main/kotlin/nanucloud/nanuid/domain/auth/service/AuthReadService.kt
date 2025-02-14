package nanucloud.nanuid.domain.auth.service

import nanucloud.nanuid.domain.auth.exception.NoPermissionException
import nanucloud.nanuid.domain.auth.exception.NotFoundException
import nanucloud.nanuid.domain.auth.presentation.response.RefreshTokenResponse
import nanucloud.nanuid.domain.auth.persistence.repository.RefreshTokenJpaRepository
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthReadService(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
    private val userFacade: UserFacade
) {

    fun execute(tokenId: String): RefreshTokenResponse {
        val userId = userFacade.getUserId()

        val token = refreshTokenJpaRepository.findById(UUID.fromString(tokenId))
            .orElseThrow { NotFoundException }

        if (token.userId != userId) {
            throw NoPermissionException
        }

        return RefreshTokenResponse(
            refreshTokenId = token.id.toString(),
            applicationId = token.applicationId,
            deviceType = token.deviceType,
            authTime = token.authTime,
            ip = token.ip,
            applicationName = token.applicationName
        )
    }
}
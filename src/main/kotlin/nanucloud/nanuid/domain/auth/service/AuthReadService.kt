package nanucloud.nanuid.domain.auth.service

import nanucloud.nanuid.domain.auth.dto.response.RefreshTokenResponse
import nanucloud.nanuid.domain.auth.persistence.repository.RefreshTokenRepository
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.stereotype.Service

@Service
class AuthReadService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userFacade: UserFacade
) {
    fun execute():List<RefreshTokenResponse> {
        val userId = userFacade.getUserId()
        println(userId)
        val userRefreshTokens =  refreshTokenRepository.findByUserId(userId)
        println(userRefreshTokens)
        return userRefreshTokens.map {
            RefreshTokenResponse(
                refreshTokenId = it.id.toString(),
                applicationId = it.applicationId,
                deviceType = it.deviceType,
                authTime = it.authTime
            )
        }
    }
}
package nanucloud.nanuid.domain.auth.service

import nanucloud.nanuid.domain.auth.dto.response.RefreshTokenResponse
import nanucloud.nanuid.domain.auth.persistence.repository.RefreshTokenRepository
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthReadService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userFacade: UserFacade
) {

    fun execute(page: Int): Page<RefreshTokenResponse> {
        val userId = userFacade.getUserId()
        val pageable: Pageable = PageRequest.of(page, 5)
        val userRefreshTokens = refreshTokenRepository.findByUserId(userId, pageable)

        val responses = userRefreshTokens.content.flatMap { token ->
            listOf(
                RefreshTokenResponse(
                    refreshTokenId = token.id.toString(),
                    applicationId = token.applicationId,
                    deviceType = token.deviceType,
                    authTime = token.authTime,
                    ip = token.ip,
                    applicationName = token.applicationName
                )
            )
        }

        return PageImpl(responses, pageable, userRefreshTokens.totalElements)
    }
}

package nanucloud.nanuid.domain.auth.service

import nanucloud.nanuid.domain.auth.exception.NoPermissionException
import nanucloud.nanuid.domain.auth.persistence.repository.RefreshTokenJpaRepository
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthDeleteService(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
    private val userFacade: UserFacade
) {

    @Transactional
    fun execute(tokenId: String) {
        val userId = userFacade.getUserId()
        val tokenUUID = try {
            UUID.fromString(tokenId)
        } catch (e: IllegalArgumentException) {
            throw NoPermissionException
        }
        print(tokenUUID)

        val token = refreshTokenJpaRepository.findById(tokenUUID) // toString() 제거
            .orElseThrow { NoPermissionException }

        if (token.userId != userId) {
            print("FUCK")
            throw NoPermissionException
        }

        refreshTokenJpaRepository.delete(token)
    }

}

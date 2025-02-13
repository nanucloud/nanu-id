package nanucloud.nanuid.domain.auth.service

import nanucloud.nanuid.domain.auth.persistence.repository.RefreshTokenJpaRepository
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.transaction.annotation.Transactional

class AuthAllDeleteService (
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute() {
        val userId = userFacade.getUserId()
        refreshTokenJpaRepository.deleteByUserId(userId)
    }
}
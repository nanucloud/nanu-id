package nanucloud.nanuid.domain.user.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.user.persistence.repository.UserRepository
import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.presentation.dto.request.UserRegisterRequest
import nanucloud.nanuid.domain.user.presentation.dto.response.UserProfileResponse
import nanucloud.nanuid.domain.user.exception.UserAlreadyExistsException
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserProfileService @Autowired constructor(
    private val userFacade: UserFacade
) {

    @Transactional
    fun execute(): UserProfileResponse {
        val user = userFacade.getCurrentUser()
        return UserProfileResponse(
            email = user.email,
            name = user.name
        )
    }
}

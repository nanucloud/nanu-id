package nanucloud.nanuid.domain.user.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.user.presentation.dto.response.UserProfileResponse
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.beans.factory.annotation.Autowired
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
            name = user.name,
            accountStatus = user.isEnabled
        )
    }
}

package nanucloud.nanuid.domain.user.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.user.repository.UserRepository
import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.dto.request.UserRegisterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserRegisterService @Autowired constructor(
    private val userRepository: UserRepository
) {

    @Transactional
    fun execute(userSignUpRequest: UserRegisterRequest): User {
        val user = User(
            deviceToken = userSignUpRequest.deviceToken,
            pin = userSignUpRequest.pin,
            password = userSignUpRequest.password,
            name = userSignUpRequest.name,
            email = userSignUpRequest.email,
            birthDate = userSignUpRequest.birthDate,
            isEnabled = true,
            isAccountLocked = false
        )
        return userRepository.save(user)
    }
}
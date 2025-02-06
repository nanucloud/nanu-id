package nanucloud.nanuid.domain.user.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.user.persistence.repository.UserRepository
import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.presentation.dto.request.UserRegisterRequest
import nanucloud.nanuid.domain.user.exception.UserAlreadyExistsException
import nanucloud.nanuid.domain.user.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserRegisterService @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
) {

    @Transactional
    fun execute(userSignUpRequest: UserRegisterRequest): User {
        userRepository.findByEmail(userSignUpRequest.email).ifPresent {
            throw UserAlreadyExistsException
        }

        val user = User(
            deviceToken = userSignUpRequest.deviceToken,
            pin = passwordEncoder.encode(userSignUpRequest.pin),
            password = passwordEncoder.encode(userSignUpRequest.password),
            name = userSignUpRequest.name,
            email = userSignUpRequest.email,
            birthDate = userSignUpRequest.birthDate,
            isEnabled = true,
            isAccountLocked = false
        )
        val userEntity = userRepository.save(userMapper.toEntity(user))
        return userMapper.toDomain(userEntity)
    }
}
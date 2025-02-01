package nanucloud.nanuid.domain.user.facade

import lombok.RequiredArgsConstructor
import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.exception.UserNotFoundException
import nanucloud.nanuid.domain.user.mapper.UserMapper
import nanucloud.nanuid.domain.user.persistence.repository.UserRepository
import org.example.pmanchu.global.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
@RequiredArgsConstructor
class UserFacade(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    fun getUserById(userId: String): User {
        val userEntity = userRepository.findById(UUID.fromString(userId))
            .orElseThrow { UserNotFoundException }

        return userMapper.toDomain(userEntity)
    }

    fun getCurrentUserName(): String {
        val id = SecurityContextHolder.getContext().authentication.name
        return getUserById(id).name
    }

    fun getUserId(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as AuthDetails
        return userDetails.getUserId()
    }

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val authDetails = authentication.principal as AuthDetails
        return authDetails.getUser()
    }

    fun saveUser(user: User): User {
        val userEntity = userMapper.toEntity(user)
        val savedEntity = userRepository.save(userEntity)
        return userMapper.toDomain(savedEntity)
    }
}

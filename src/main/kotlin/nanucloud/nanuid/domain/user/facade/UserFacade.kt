package nanucloud.nanuid.domain.user.facade

import lombok.RequiredArgsConstructor
import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.exception.UserNotFoundException
import nanucloud.nanuid.domain.user.repository.UserRepository
import org.example.pmanchu.global.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
@RequiredArgsConstructor
class UserFacade (
    private val userRepository: UserRepository
){
    fun getUserById(userId : String): User {
        val user = userRepository.findById(UUID.fromString(userId))
        if (user.isEmpty) {
            throw UserNotFoundException
        }
        return user.get()
    }

    fun getCurrentUserName(): User {
        val id = SecurityContextHolder.getContext().authentication.name
        return getUserById(id)
    }

    fun getUserId(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as AuthDetails
        return userDetails.getUserId()
    }

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as AuthDetails
        return userDetails.getUser()
    }
}
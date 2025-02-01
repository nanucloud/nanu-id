package nanucloud.nanuid.global.security.auth

import nanucloud.nanuid.domain.user.persistence.repository.UserRepository
import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.example.pmanchu.global.security.auth.AuthDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AuthDetailsService (
    private val userFacade: UserFacade
) : UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val user: User = userFacade.getUserById(id);
        return AuthDetails(user);
    }
}
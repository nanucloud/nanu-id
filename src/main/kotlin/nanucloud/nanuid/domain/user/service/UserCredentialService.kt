package nanucloud.nanuid.domain.user.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserCredentialService @Autowired constructor(
    private val userFacade: UserFacade
) {
    @Transactional
    fun get(authScope: AuthScope): String = with(userFacade.getCurrentUser()) {
        when (authScope) {
            AuthScope.NAME_ACCESS -> name
            AuthScope.EMAIL_ACCESS -> email
            AuthScope.FULL_ACCESS -> email
            AuthScope.USERID_ACCESS -> userId.toString()
            AuthScope.BIRTH_ACCESS -> birthDate.toString()
        }
    }
}
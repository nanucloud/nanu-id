package nanucloud.nanuid.domain.user.repository

import nanucloud.nanuid.domain.user.domain.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository:CrudRepository<User,UUID>{
    fun findByEmail(email: String): Optional<User>
}
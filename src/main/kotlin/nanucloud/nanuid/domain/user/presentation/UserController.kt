package nanucloud.nanuid.domain.user.presentation

import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.dto.request.UserRegisterRequest
import nanucloud.nanuid.domain.user.service.UserRegisterService
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/auth")
class UserController (
    private val userRegisterService: UserRegisterService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userRegisterRequest: UserRegisterRequest): User {
        return userRegisterService.execute(userRegisterRequest);
    }
}

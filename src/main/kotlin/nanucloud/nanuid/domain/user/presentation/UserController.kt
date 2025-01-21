package nanucloud.nanuid.domain.user.presentation

import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.dto.request.UserLoginRequest
import nanucloud.nanuid.domain.user.dto.request.UserRegisterRequest
import nanucloud.nanuid.domain.user.service.UserPrimaryLoginService
import nanucloud.nanuid.domain.user.service.UserRegisterService
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/auth")
class UserController (
    private val userRegisterService: UserRegisterService,
    private val userPrimaryLoginService: UserPrimaryLoginService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userRegisterRequest: UserRegisterRequest): User {
        return userRegisterService.execute(userRegisterRequest);
    }

    @PostMapping("/login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest): TokenResponse {
        return userPrimaryLoginService.execute(userLoginRequest);
    }
}

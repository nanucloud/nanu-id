package nanucloud.nanuid.domain.user.presentation

import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.presentation.dto.request.UserLoginRequest
import nanucloud.nanuid.domain.user.presentation.dto.request.UserRegisterRequest
import nanucloud.nanuid.domain.user.presentation.dto.request.UserReissueRequest
import nanucloud.nanuid.domain.user.presentation.dto.response.UserProfileResponse
import nanucloud.nanuid.domain.user.service.UserPrimaryLoginService
import nanucloud.nanuid.domain.user.service.UserProfileService
import nanucloud.nanuid.domain.user.service.UserRegisterService
import nanucloud.nanuid.domain.user.service.UserReissueService
import nanucloud.nanuid.global.security.auth.RequiredAuthScope
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/auth")
class UserController (
    private val userRegisterService: UserRegisterService,
    private val userPrimaryLoginService: UserPrimaryLoginService,
    private val userProfileService: UserProfileService,
    private val userReissueService: UserReissueService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userRegisterRequest: UserRegisterRequest): User {
        return userRegisterService.execute(userRegisterRequest);
    }

    @PostMapping("/login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest): TokenResponse {
        return userPrimaryLoginService.execute(userLoginRequest);
    }

    @RequiredAuthScope(["FULL_ACCESS"])
    @GetMapping("/userinfo")
    fun getUserProfile(): UserProfileResponse {
        return userProfileService.execute()
    }

    @PostMapping("/reissue")
    fun reissueToken(@RequestBody userReissueRequest: UserReissueRequest): TokenResponse {
        return userReissueService.execute(userReissueRequest.refreshToken)
    }
}

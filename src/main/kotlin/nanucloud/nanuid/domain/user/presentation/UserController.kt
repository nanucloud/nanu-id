package nanucloud.nanuid.domain.user.presentation

import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.presentation.dto.request.UserLoginRequest
import nanucloud.nanuid.domain.user.presentation.dto.request.UserOAuthLoginRequest
import nanucloud.nanuid.domain.user.presentation.dto.request.UserRegisterRequest
import nanucloud.nanuid.domain.user.presentation.dto.request.UserReissueRequest
import nanucloud.nanuid.domain.user.presentation.dto.response.UserOAuthLoginResponse
import nanucloud.nanuid.domain.user.presentation.dto.response.UserProfileResponse
import nanucloud.nanuid.domain.user.service.*
import nanucloud.nanuid.global.security.auth.RequiredAuthScope
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class UserController (
    private val userRegisterService: UserRegisterService,
    private val userPrimaryLoginService: UserPrimaryLoginService,
    private val userProfileService: UserProfileService,
    private val userReissueService: UserReissueService,
    private val userOAuthPrimaryLoginService: UserOAuthPrimaryLoginService,
    private val userCredentialService: UserCredentialService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userRegisterRequest: UserRegisterRequest): User {
        return userRegisterService.execute(userRegisterRequest);
    }

    @PostMapping("/login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest): TokenResponse {
        return userPrimaryLoginService.execute(userLoginRequest);
    }

    @PostMapping("/o/login")
    fun oauthLogin(@RequestBody userOAuthLoginRequest: UserOAuthLoginRequest): UserOAuthLoginResponse {
        return userOAuthPrimaryLoginService.execute(userOAuthLoginRequest)
    }

    @RequiredAuthScope(["FULL_ACCESS"])
    @GetMapping("/userinfo")
    fun getUserProfile(): UserProfileResponse {
        return userProfileService.execute()
    }

    @RequiredAuthScope(["EMAIL_ACCESS"])
    @GetMapping("/get/email")
    fun getEmail(): String {
        return userCredentialService.get(AuthScope.EMAIL_ACCESS)
    }

    @RequiredAuthScope(["NAME_ACCESS"])
    @GetMapping("/get/name")
    fun getName(): String {
        return userCredentialService.get(AuthScope.NAME_ACCESS)
    }

    @PostMapping("/reissue")
    fun reissueToken(@RequestBody userReissueRequest: UserReissueRequest): TokenResponse {
        return userReissueService.execute(userReissueRequest.refreshToken)
    }
}

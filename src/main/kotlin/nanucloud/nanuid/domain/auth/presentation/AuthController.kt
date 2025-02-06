package nanucloud.nanuid.domain.auth.presentation

import nanucloud.nanuid.domain.auth.dto.response.RefreshTokenResponse
import nanucloud.nanuid.domain.auth.service.AuthReadService
import nanucloud.nanuid.global.security.auth.RequiredAuthScope
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token")
class AuthController (
    private val authReadService: AuthReadService
) {
    @RequiredAuthScope(["FULL_ACCESS"])
    @GetMapping("/getTokens")
    fun getAllTokens(
        @RequestParam page: Int = 0
    ): Page<RefreshTokenResponse> {
        return authReadService.execute(page)
    }
}
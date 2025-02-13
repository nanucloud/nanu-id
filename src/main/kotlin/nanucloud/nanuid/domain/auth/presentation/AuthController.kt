package nanucloud.nanuid.domain.auth.presentation

import nanucloud.nanuid.domain.auth.presentation.response.RefreshTokenResponse
import nanucloud.nanuid.domain.auth.service.AuthDeleteService
import nanucloud.nanuid.domain.auth.service.AuthReadService
import nanucloud.nanuid.global.security.auth.RequiredAuthScope
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/token")
class AuthController (
    private val authReadService: AuthReadService,
    private val authDeleteService: AuthDeleteService
) {
    @RequiredAuthScope(["FULL_ACCESS"])
    @GetMapping("/getTokens")
    fun getAllTokens(
        @RequestParam page: Int = 0
    ): Page<RefreshTokenResponse> {
        return authReadService.execute(page)
    }

    @RequiredAuthScope(["FULL_ACCESS"])
    @DeleteMapping("/delete/{tokenId}")
    fun deleteToken(@PathVariable tokenId:String) {
        authDeleteService.execute(tokenId)
    }
}
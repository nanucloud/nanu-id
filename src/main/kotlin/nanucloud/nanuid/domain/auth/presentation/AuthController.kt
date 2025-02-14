package nanucloud.nanuid.domain.auth.presentation

import nanucloud.nanuid.domain.auth.presentation.response.RefreshTokenResponse
import nanucloud.nanuid.domain.auth.service.AuthDeleteService
import nanucloud.nanuid.domain.auth.service.AuthAllReadService
import nanucloud.nanuid.domain.auth.service.AuthReadService
import nanucloud.nanuid.global.security.auth.RequiredAuthScope
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/token")
class AuthController(
    private val authAllReadService: AuthAllReadService,
    private val authDeleteService: AuthDeleteService,
    private val authReadService: AuthReadService
) {
    @RequiredAuthScope(["FULL_ACCESS"])
    @GetMapping("/getTokens")
    fun getAllTokens(
        @RequestParam page: Int = 0
    ): Page<RefreshTokenResponse> {
        return authAllReadService.execute(page)
    }

    @RequiredAuthScope(["FULL_ACCESS"])
    @DeleteMapping("/delete/{tokenId}")
    fun deleteToken(@PathVariable tokenId: String): HttpStatus {
        authDeleteService.execute(tokenId)
        return HttpStatus.OK
    }

    @RequiredAuthScope(["FULL_ACCESS"])
    @GetMapping("/get/{tokenId}")
    fun getToken(@PathVariable tokenId: String): RefreshTokenResponse {
        return authReadService.execute(tokenId)
    }
}
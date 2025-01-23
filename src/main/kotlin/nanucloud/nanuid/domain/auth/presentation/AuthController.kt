package nanucloud.nanuid.domain.auth.presentation

import nanucloud.nanuid.domain.auth.dto.response.RefreshTokenResponse
import nanucloud.nanuid.domain.auth.service.AuthReadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token")
class AuthController (
    private val authReadService: AuthReadService
) {
    @GetMapping("/getAll")
    fun getAllTokens(): List<RefreshTokenResponse> {
        return authReadService.execute()
    }
}
package nanucloud.nanuid.domain.oauth.presentation

import nanucloud.nanuid.domain.oauth.presentation.dto.request.OAuthTokenRequest
import nanucloud.nanuid.domain.oauth.service.OAuthCodeIssueService
import nanucloud.nanuid.global.security.jwt.dto.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuthController(
    private val oAuthCodeIssueService: OAuthCodeIssueService
) {
    @PostMapping("/oauth/code")
    fun issueOauthCode(@RequestBody oAuthTokenRequest: OAuthTokenRequest): TokenResponse {
        return oAuthCodeIssueService.execute(oAuthTokenRequest)
    }
}
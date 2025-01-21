package nanucloud.nanuid.domain.user.client

import nanucloud.nanuid.domain.user.dto.response.RecaptchaResponse
import nanucloud.nanuid.global.config.FeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "recaptchaClient",
    url = "https://www.google.com/recaptcha/api/siteverify",
    configuration = [FeignConfig::class]
)
interface RecaptchaClient {
    @PostMapping(headers = ["content-length: 0"])
    fun verifyRecaptcha(
        @RequestParam("secret") secretKey: String,
        @RequestParam("response") recaptchaToken: String
    ): RecaptchaResponse
}

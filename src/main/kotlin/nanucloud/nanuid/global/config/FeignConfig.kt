package nanucloud.nanuid.global.config

import feign.RequestInterceptor
import feign.RequestTemplate
import feign.okhttp.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {

    @Bean
    fun okhttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}
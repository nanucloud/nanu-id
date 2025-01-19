package nanucloud.nanuid.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "spring.jwt")
data class JwtProperties(
    val jwtSecret: String,
    val accessExp: Long,
    val refreshExp: Long,
    val header: String,
    val prefix: String
)

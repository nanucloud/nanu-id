package nanucloud.nanuid

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class NanuOauthApplication

fun main(args: Array<String>) {
	runApplication<NanuOauthApplication>(*args)
}

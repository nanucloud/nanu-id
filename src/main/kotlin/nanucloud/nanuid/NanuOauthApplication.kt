package nanucloud.nanuid

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients
@EnableAspectJAutoProxy
@ComponentScan

class NanuOauthApplication

fun main(args: Array<String>) {
	runApplication<NanuOauthApplication>(*args)
}

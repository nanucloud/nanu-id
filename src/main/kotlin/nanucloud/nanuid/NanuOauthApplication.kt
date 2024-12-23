package nanucloud.nanuid

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NanuOauthApplication

fun main(args: Array<String>) {
	runApplication<NanuOauthApplication>(*args)
}

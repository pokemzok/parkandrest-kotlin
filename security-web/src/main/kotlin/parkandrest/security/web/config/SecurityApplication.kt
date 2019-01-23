package parkandrest.security.web.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.EnableTransactionManagement
import parkandrest.security.core.config.SecurityCoreConfig

@Import(
        SecurityCoreConfig::class,
        SubscriberConfig::class
)
@EnableTransactionManagement
@SpringBootApplication
class SecurityApplication

fun main(args: Array<String>) {
    SpringApplication.run(SecurityApplication::class.java, *args)
}


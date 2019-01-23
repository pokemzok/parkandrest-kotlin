package parkandrest.security.core.config


import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["parkandrest.security"])
@EntityScan(basePackages = ["parkandrest.security"])
@ComponentScan("parkandrest.security")
@PropertySource("classpath:security.properties")
class SecurityCoreConfig

package parkandrest.parkingmanagement.core.config


import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["parkandrest.parkingmanagement.core"])
@EntityScan(basePackages = ["parkandrest.parkingmanagement.core"])
@ComponentScan("parkandrest.parkingmanagement.core")
class ParkingManagementConfig

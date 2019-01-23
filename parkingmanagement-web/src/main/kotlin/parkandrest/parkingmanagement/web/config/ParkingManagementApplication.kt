package parkandrest.parkingmanagement.web.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.EnableTransactionManagement
import parkandrest.parkingmanagement.core.config.ParkingManagementConfig

@Import(
        ParkingManagementConfig::class,
        SubscriberConfig::class
)
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan("parkandrest.parkingmanagement.web")
class ParkingManagementApplication

    fun main(args: Array<String>) {
        SpringApplication.run(ParkingManagementApplication::class.java, *args)
    }


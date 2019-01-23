package parkandrest.timemanagement.web.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
@Import(RabbitPublisherConfig::class)
@ComponentScan("parkandrest.timemanagement.web")
class TimeManagementApplication

    fun main(args: Array<String>) {
        SpringApplication.run(TimeManagementApplication::class.java, *args)
    }


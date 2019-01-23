package parkandrest.database

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DatabaseApplication

fun main(args: Array<String>) {
    runApplication<DatabaseApplication>(*args)
}

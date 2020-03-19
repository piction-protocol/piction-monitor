package network.piction.monitor

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
open class MonitorApplication

fun main(args: Array<String>) {
    runApplication<MonitorApplication>(*args)
}
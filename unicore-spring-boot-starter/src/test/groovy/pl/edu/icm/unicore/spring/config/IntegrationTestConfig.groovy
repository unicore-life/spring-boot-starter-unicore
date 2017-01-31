package pl.edu.icm.unicore.spring.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import pl.edu.icm.unicore.spring.UnicoreAutoConfiguration

@TestConfiguration
@EnableAutoConfiguration
@Import(UnicoreAutoConfiguration)
class IntegrationTestConfig {
}

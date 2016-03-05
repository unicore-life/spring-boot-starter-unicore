package pl.edu.icm.unicore.spring.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import pl.edu.icm.unicore.spring.UnicoreAutoConfiguration

@Configuration
@EnableAutoConfiguration
@Import(UnicoreAutoConfiguration)
class IntegrationTestConfig {
}

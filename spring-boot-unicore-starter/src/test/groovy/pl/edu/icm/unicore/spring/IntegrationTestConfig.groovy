package pl.edu.icm.unicore.spring

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableAutoConfiguration
@Import(UnicoreAutoConfiguration)
class IntegrationTestConfig {
}

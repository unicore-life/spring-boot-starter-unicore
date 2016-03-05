package pl.edu.icm.unicore.spring.config

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.boot.test.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@SpringApplicationConfiguration(
        classes = [IntegrationTestConfig],
        initializers = [ConfigFileApplicationContextInitializer]
)
@TestPropertySource('classpath:application-test.yaml')
abstract class IntegrationTestSpec extends Specification {
    @Rule
    WireMockRule wireMockServer = new WireMockRule(8080)
}

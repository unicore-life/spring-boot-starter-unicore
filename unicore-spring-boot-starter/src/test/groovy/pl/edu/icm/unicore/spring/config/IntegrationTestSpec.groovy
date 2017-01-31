package pl.edu.icm.unicore.spring.config

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@ContextConfiguration(
        classes = [IntegrationTestConfig],
        initializers = [ConfigFileApplicationContextInitializer]
)
@TestPropertySource('classpath:application-test.properties')
abstract class IntegrationTestSpec extends Specification {
    @Rule
    WireMockRule wireMockServer = new WireMockRule(8080)
}

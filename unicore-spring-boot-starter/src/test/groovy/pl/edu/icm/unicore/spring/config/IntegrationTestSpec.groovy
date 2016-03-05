package pl.edu.icm.unicore.spring.config

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.boot.test.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(
        classes = [IntegrationTestConfig],
        initializers = ConfigFileApplicationContextInitializer,
        loader = SpringApplicationContextLoader
)
abstract class IntegrationTestSpec extends Specification {
    @Rule
    WireMockRule wireMockServer = new WireMockRule(8080)
}

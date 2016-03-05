package pl.edu.icm.unicore.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import pl.edu.icm.unicore.spring.config.IntegrationTestConfig
import pl.edu.icm.unicore.spring.config.IntegrationTestSpec
import spock.lang.Specification

//@ActiveProfiles('test')
@ContextConfiguration(
        classes = [IntegrationTestConfig],
        initializers = ConfigFileApplicationContextInitializer,
        loader = SpringApplicationContextLoader
)
class UnicorePropertiesTest extends Specification {
    @Autowired
    UnicoreProperties unicoreProperties

    def 'should autowire unicoreProperties'() {
        expect:
        unicoreProperties != null
    }

    def 'should have default property value'() {
        expect:
        unicoreProperties.identityConfig == 'classpath:grid-test.properties'
    }
}

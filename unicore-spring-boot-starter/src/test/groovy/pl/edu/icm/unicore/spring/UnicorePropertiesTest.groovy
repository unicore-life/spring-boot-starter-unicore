package pl.edu.icm.unicore.spring

import org.springframework.beans.factory.annotation.Autowired
import pl.edu.icm.unicore.spring.config.IntegrationTestSpec

class UnicorePropertiesTest extends IntegrationTestSpec {
    @Autowired
    private UnicoreProperties unicoreProperties

    def 'should read identity config path from application properties'() {
        expect:
        unicoreProperties.identityConfig == 'classpath:grid-test.properties'
    }
}

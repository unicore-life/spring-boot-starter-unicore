package pl.edu.icm.unicore.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = [IntegrationTestConfig])
class SomeTest extends Specification {

    @Autowired
    Some some

    def 'should autowire some'() {
        expect:
        some != null
    }
}

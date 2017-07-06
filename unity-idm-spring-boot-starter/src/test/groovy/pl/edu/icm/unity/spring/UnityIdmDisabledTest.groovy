package pl.edu.icm.unity.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@SpringBootTest(classes = [UnityAutoConfiguration])
class UnityIdmDisabledTest extends Specification {
    @Autowired
    private ApplicationContext context

    def 'should properties bean be disabled'() {
        expect:
        context.getBeanNamesForType(UnityIdmProperties).length == 0
    }
}

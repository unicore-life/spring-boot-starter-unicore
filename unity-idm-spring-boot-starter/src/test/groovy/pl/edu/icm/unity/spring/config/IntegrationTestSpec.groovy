package pl.edu.icm.unity.spring.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import pl.edu.icm.unity.spring.UnityAutoConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [UnityAutoConfiguration])
@TestPropertySource('classpath:application-test.properties')
abstract class IntegrationTestSpec extends Specification {
}

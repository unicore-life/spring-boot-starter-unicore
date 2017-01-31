package pl.edu.icm.unity.spring

import org.springframework.beans.factory.annotation.Autowired
import pl.edu.icm.unity.spring.config.IntegrationTestSpec

class UnityIdmPropertiesTest extends IntegrationTestSpec {
    @Autowired
    private UnityIdmProperties unityIdmProperties;

    def 'should get IDP url from property file'() {
        expect:
        unityIdmProperties.idpUrl.endsWith('test')
    }
}

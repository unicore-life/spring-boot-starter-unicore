package pl.edu.icm.unity.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pl.edu.icm.unity.spring.saml.SamlAuthenticationHandler;
import pl.edu.icm.unity.spring.saml.SamlRequestHandler;
import pl.edu.icm.unity.spring.saml.SamlResponseHandler;
import pl.edu.icm.unity.spring.saml.SamlSingleLogoutHandler;
import pl.edu.icm.unity.spring.slo.UnitySingleLogoutHandlerSupplier;

@ComponentScan("pl.edu.icm.unity.spring")
//@ConditionalOnProperty(value = "unity-idm.enabled", havingValue = "true")
@EnableConfigurationProperties({UnityIdmProperties.class})
@SpringBootConfiguration
public class UnityAutoConfiguration {

    @Autowired
    @Bean
    public SamlAuthenticationHandler samlAuthenticationHandler(SamlRequestHandler samlRequestHandler,
                                                               SamlResponseHandler samlResponseHandler,
                                                               UnityIdmProperties unityIdmProperties) {
        return new SamlAuthenticationHandler(samlRequestHandler, samlResponseHandler, unityIdmProperties);
    }

    @Autowired
    @Bean
    public UnitySingleLogoutHandlerSupplier unitySingleLogoutHandlerSupplier(
            SamlSingleLogoutHandler singleLogoutHandler,
            UnityIdmProperties unityIdmProperties) {
        return new UnitySingleLogoutHandlerSupplier(singleLogoutHandler, unityIdmProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SamlRequestHandler samlRequestHandler() {
        return new SamlRequestHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public SamlResponseHandler samlResponseHandler() {
        return new SamlResponseHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public SamlSingleLogoutHandler samlSingleLogoutHandler() {
        return new SamlSingleLogoutHandler();
    }

    private Log log = LogFactory.getLog(UnityAutoConfiguration.class);
}

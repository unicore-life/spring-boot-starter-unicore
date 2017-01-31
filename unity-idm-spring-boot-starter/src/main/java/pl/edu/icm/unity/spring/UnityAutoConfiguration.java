package pl.edu.icm.unity.spring;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConditionalOnProperty(value = "unity-idm.enabled", havingValue = "true")
@EnableConfigurationProperties(value = {UnityIdmProperties.class})
@SpringBootConfiguration
public class UnityAutoConfiguration {
}

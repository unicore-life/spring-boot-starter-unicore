package pl.edu.icm.unicore.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "unicore")
public class UnicoreProperties {
    @Value("${identityConfig:100}")
    private String identityConfig;
    @Value("${registry:5000}")
    private String registry;

    public String getIdentityConfig() {
        return identityConfig;
    }

    public String getRegistry() {
        return registry;
    }
}

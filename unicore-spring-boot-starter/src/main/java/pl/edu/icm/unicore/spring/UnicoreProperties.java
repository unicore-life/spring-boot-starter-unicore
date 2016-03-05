package pl.edu.icm.unicore.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "unicore")
public final class UnicoreProperties {
    //    @Value("${identityConfig:classpath:grid-test.properties}")
    private String identityConfig = "classpath:grid-test.properties2";
    //    @Value("${registry:5000}")
    private String registry = "reg";

    public String getIdentityConfig() {
        return identityConfig;
    }

    public String getRegistry() {
        return registry;
    }

    public void setIdentityConfig(String identityConfig) {
        this.identityConfig = identityConfig;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }
}

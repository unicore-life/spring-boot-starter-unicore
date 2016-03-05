package pl.edu.icm.unicore.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "unicore")
public final class UnicoreProperties {
    private String identityConfig = "./config/grid-identity.properties";
    private String registryUri = "https://localhost:8080/REGISTRY/services/Registry?res=default_registry";

    public String getIdentityConfig() {
        return identityConfig;
    }

    public void setIdentityConfig(String identityConfig) {
        this.identityConfig = identityConfig;
    }

    public String getRegistryUri() {
        return registryUri;
    }

    public void setRegistryUri(String registryUri) {
        this.registryUri = registryUri;
    }
}

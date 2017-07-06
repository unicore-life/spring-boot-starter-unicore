package pl.edu.icm.unity.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "unity-idm")
public final class UnityIdmProperties {
    private String idpUrl = "https://localhost/context/saml2unicoreIdp-web";
    private String idpSloUrl = "https://localhost/context/SLO-WEB";
    private String targetUrl = "https://localhost/sign-in";

    public String getIdpUrl() {
        return idpUrl;
    }

    public void setIdpUrl(String idpUrl) {
        this.idpUrl = idpUrl;
    }

    public String getIdpSloUrl() {
        return idpSloUrl;
    }

    public void setIdpSloUrl(String idpSloUrl) {
        this.idpSloUrl = idpSloUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}

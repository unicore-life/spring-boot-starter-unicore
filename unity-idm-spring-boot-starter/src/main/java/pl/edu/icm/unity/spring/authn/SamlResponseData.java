package pl.edu.icm.unity.spring.authn;

import eu.unicore.security.etd.TrustDelegation;
import pl.edu.icm.unity.spring.slo.UserUnityAttributes;

import java.util.List;

public class SamlResponseData {
    private String returnUrl;
    private String sessionIndex;

    private List<TrustDelegation> trustDelegations;
    private UserUnityAttributes userUnityAttributes = new UserUnityAttributes();

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSessionIndex() {
        return sessionIndex;
    }

    public void setSessionIndex(String sessionIndex) {
        this.sessionIndex = sessionIndex;
    }

    public UserUnityAttributes getUserUnityAttributes() {
        return userUnityAttributes;
    }

    public void storeAttribute(String key, String value) {
        userUnityAttributes.store(key, value);
    }

    public List<TrustDelegation> getTrustDelegations() {
        return trustDelegations;
    }

    public void storeTrustDelegations(List<TrustDelegation> trustDelegationList) {
        this.trustDelegations = trustDelegationList;
    }
}

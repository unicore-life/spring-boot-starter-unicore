package pl.edu.icm.unity.spring.authn;

import eu.unicore.security.etd.TrustDelegation;
import pl.edu.icm.unity.spring.user.UserAttributes;

import java.util.List;

/**
 * Created by rafal.kluszczynski on 02/02/17.
 */
public class SamlResponseData {
    private String returnUrl;
    private String sessionIndex;

    private List<TrustDelegation> trustDelegations;
    private UserAttributes userAttributes = new UserAttributes();

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

    public void storeAttribute(String key, String value) {
        userAttributes.store(key, value);
    }

    public void storeTrustDelegations(List<TrustDelegation> trustDelegationList) {
        this.trustDelegations = trustDelegationList;
    }
}

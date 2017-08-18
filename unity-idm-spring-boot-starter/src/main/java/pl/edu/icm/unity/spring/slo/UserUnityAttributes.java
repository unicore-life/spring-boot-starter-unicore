package pl.edu.icm.unity.spring.slo;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class UserUnityAttributes {
    private String commonName;
    private String emailAddress;
    private String custodianDn;

    private final Set<String> memberGroups = new TreeSet<>();
    private final Map<String, String> others = new ConcurrentHashMap<>();

    public UserUnityAttributes() {
    }

    public String getCommonName() {
        return commonName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCustodianDn() {
        return custodianDn;
    }

    public Set<String> getMemberGroups() {
        return memberGroups;
    }

    public String getAttribute(String key) {
        return others.get(key);
    }

    public Map<String, String> getOthers() {
        return others;
    }

    @Override
    public String toString() {
        return String.format(
                "UserUnityAttributes{commonName='%s', emailAddress='%s', custodianDn='%s', memberGroups=%s}",
                commonName, emailAddress, custodianDn, memberGroups);
    }

    /**
     * Method that stores user attributes got from Unity IDM response.
     *
     * @param name property name
     * @param value property value
     */
    public void store(String name, String value) {
        switch (name) {
            case "cn":
                commonName = value;
                break;
            case "email":
                emailAddress = value;
                break;
            case "TrustDelegationOfUser":
                custodianDn = value;
                break;
            case "memberOf":
                memberGroups.add(value);
                break;
            default:
                others.put(name, value);
        }
    }
}

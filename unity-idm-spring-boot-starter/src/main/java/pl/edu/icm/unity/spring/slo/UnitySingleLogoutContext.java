package pl.edu.icm.unity.spring.slo;

import eu.emi.security.authn.x509.X509Credential;

public interface UnitySingleLogoutContext {

    String getPrincipalCommonName();

    String getSessionIndex();

    X509Credential getGridCredential();
}

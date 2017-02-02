package pl.edu.icm.unity.spring.api;

import eu.emi.security.authn.x509.X509CertChainValidatorExt;
import eu.emi.security.authn.x509.X509Credential;

public interface IdentityProvider {

    X509Credential getGridCredential();

    X509CertChainValidatorExt getGridValidator();

    X509CertChainValidatorExt getIdpValidator();
}

package pl.edu.icm.unity.spring.saml;

import eu.emi.security.authn.x509.X509CertChainValidatorExt;
import eu.emi.security.authn.x509.X509Credential;
import eu.unicore.samly2.exceptions.SAMLValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.edu.icm.unity.spring.UnityIdmProperties;
import pl.edu.icm.unity.spring.authn.SamlResponseData;
import xmlbeans.org.oasis.saml2.protocol.LogoutResponseDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SamlAuthenticationHandler {
    private final SamlRequestHandler samlRequestHandler;
    private final SamlResponseHandler samlResponseHandler;
    private final UnityIdmProperties unityIdmProperties;

    public SamlAuthenticationHandler(SamlRequestHandler samlRequestHandler,
                                     SamlResponseHandler samlResponseHandler,
                                     UnityIdmProperties unityIdmProperties) {
        this.samlRequestHandler = samlRequestHandler;
        this.samlResponseHandler = samlResponseHandler;
        this.unityIdmProperties = unityIdmProperties;
    }

    public void performAuthenticationRequest(HttpServletResponse response,
                                             String authenticationRequestId,
                                             X509Credential gridCredential) {
        samlRequestHandler.performAuthenticationRequest(response,
                unityIdmProperties.getIdpUrl(),
                authenticationRequestId,
                unityIdmProperties.getTargetUrl(),
                gridCredential);
    }

    public SamlResponseData processAuthenticationResponse(HttpServletRequest request,
                                                          String authenticationRequestId,
                                                          X509Credential gridCredential,
                                                          X509CertChainValidatorExt idpValidator) {
        return samlResponseHandler.processAuthenticationResponse(
                request, authenticationRequestId, unityIdmProperties.getTargetUrl(), gridCredential, idpValidator);
    }

    public String processSingleLogoutResponse(HttpServletRequest request) {
        try {
            final String samlResponse = request.getParameter("SAMLResponse");
            final LogoutResponseDocument messageXml = UtilitiesHelper.decodeMessage(samlResponse, log);
            log.info("Single logout response message: " + messageXml.xmlText());
        } catch (SAMLValidationException e) {
            log.error("Could not read single logout response message!", e);
        }
        return "redirect:/";
    }

    private Log log = LogFactory.getLog(SamlAuthenticationHandler.class);
}

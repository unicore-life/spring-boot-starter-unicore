package pl.edu.icm.unity.spring.authn;

import eu.unicore.samly2.exceptions.SAMLValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.icm.unity.spring.api.IdentityProvider;
import xmlbeans.org.oasis.saml2.protocol.LogoutResponseDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pl.edu.icm.unity.spring.authn.Utils.decodeMessage;


@Service
public class SamlAuthenticationHandler {
    private final SamlRequestHandler samlRequestHandler;
    private final SamlResponseHandler samlResponseHandler;

    @Autowired
    public SamlAuthenticationHandler(SamlRequestHandler samlRequestHandler,
                                     SamlResponseHandler samlResponseHandler) {
        this.samlRequestHandler = samlRequestHandler;
        this.samlResponseHandler = samlResponseHandler;
    }

    public void performAuthenticationRequest(HttpServletResponse response,
                                             String idpUrl,
                                             String authenticationRequestId,
                                             String targetUrl,
                                             IdentityProvider identityProvider) {
        samlRequestHandler
                .performAuthenticationRequest(response, idpUrl, authenticationRequestId, targetUrl, identityProvider);
    }

    public String processAuthenticationResponse(HttpServletRequest request,
                                                String authenticationRequestId,
                                                String returnUrl,
                                                String targetUrl,
                                                IdentityProvider identityProvider) {
        final SamlResponseData responseData = samlResponseHandler
                .processAuthenticationResponse(request, authenticationRequestId, targetUrl, identityProvider);

        return String.format("redirect:%s", returnUrl);
    }

    public String processSingleLogoutResponse(HttpServletRequest request) {
        try {
            final String samlResponse = request.getParameter("SAMLResponse");
            final LogoutResponseDocument messageXml = decodeMessage(samlResponse, log);
            log.info("Single logout response message: " + messageXml.xmlText());
        } catch (SAMLValidationException e) {
            log.error("Could not read single logout response message!", e);
        }
        return "redirect:/";
    }

    private Log log = LogFactory.getLog(SamlAuthenticationHandler.class);
}

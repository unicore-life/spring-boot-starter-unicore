package pl.edu.icm.unity.spring.saml;

import eu.emi.security.authn.x509.X509Credential;
import eu.unicore.samly2.binding.HttpPostBindingSupport;
import eu.unicore.samly2.binding.SAMLMessageType;
import eu.unicore.samly2.exceptions.SAMLResponderException;
import eu.unicore.samly2.proto.LogoutRequest;
import eu.unicore.security.dsig.DSigException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xmlbeans.org.oasis.saml2.assertion.NameIDType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static pl.edu.icm.unity.spring.saml.UtilitiesHelper.DEFAULT_LOGOUT_REQUEST_VALIDITY_IN_MILLIS;
import static pl.edu.icm.unity.spring.saml.UtilitiesHelper.configureHttpResponse;
import static pl.edu.icm.unity.spring.saml.UtilitiesHelper.convertDistinguishedNameToNameIdType;

public class SamlSingleLogoutHandler {

    /**
     * TODO.
     *
     * @param response                response
     * @param singleLogoutEndpointUrl single logout endpoint url
     * @param principalCommonName     principal common name
     * @param sessionIndex            session index
     * @param gridCredential          client credentials
     * @return true if single logout was processed successfully
     */
    public boolean performSingleLogoutRequest(HttpServletResponse response,
                                              final String singleLogoutEndpointUrl,
                                              final String principalCommonName,
                                              final String sessionIndex,
                                              X509Credential gridCredential) {
        final String localSamlId = gridCredential.getSubjectName();

        if (singleLogoutEndpointUrl == null || singleLogoutEndpointUrl.isEmpty()) {
            return false;
        }
        try {
            LogoutRequest logoutRequest = createLogoutRequest(
                    singleLogoutEndpointUrl, principalCommonName, sessionIndex, localSamlId, gridCredential);

            log.debug("Returning redirect with SLO request with HTTP POST binding to: " + singleLogoutEndpointUrl);
            configureHttpResponse(response);
            String htmlFormContent = HttpPostBindingSupport.getHtmlPOSTFormContents(
                    SAMLMessageType.SAMLRequest,
                    singleLogoutEndpointUrl,
                    logoutRequest.getXMLBeanDoc().xmlText(),
                    null);
            log.trace(String.format("Returning HTML content: %s", htmlFormContent));

            PrintWriter writer = response.getWriter();
            writer.write(htmlFormContent);
            writer.flush();

            return true;
        } catch (IOException | SAMLResponderException exception) {
            log.error("Could not respond with logout form!", exception);
        }
        return false;
    }

    /**
     * TODO.
     *
     * @param singleLogoutEndpoint  single logout endpoint
     * @param principalCommonName   principal common name
     * @param authoritySessionIndex authority session index
     * @param localSamlId           local SAML id
     * @param gridCredential        client credentials
     * @return logout request
     * @throws SAMLResponderException thrown when problem occurs during signing request
     */
    LogoutRequest createLogoutRequest(String singleLogoutEndpoint,
                                      String principalCommonName,
                                      String authoritySessionIndex,
                                      String localSamlId,
                                      X509Credential gridCredential) throws SAMLResponderException {
        final NameIDType toBeLoggedOutIdentity = convertDistinguishedNameToNameIdType(principalCommonName);
        final NameIDType portalGridIdentityIssuer = convertDistinguishedNameToNameIdType(localSamlId);

        LogoutRequest request = new LogoutRequest(portalGridIdentityIssuer, toBeLoggedOutIdentity);
        request.setNotAfter(new Date(System.currentTimeMillis() + DEFAULT_LOGOUT_REQUEST_VALIDITY_IN_MILLIS));
        request.setSessionIds(authoritySessionIndex);
        request.getXMLBean().setDestination(singleLogoutEndpoint);

        try {
            request.sign(gridCredential.getKey(), gridCredential.getCertificateChain());
        } catch (DSigException e) {
            log.warn("Unable to sign SLO request", e);
            throw new SAMLResponderException("Internal server error signing request.");
        }
        return request;
    }

    private Log log = LogFactory.getLog(SamlSingleLogoutHandler.class);
}

package pl.edu.icm.unity.spring.authn;

import eu.emi.security.authn.x509.X509Credential;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.edu.icm.unity.spring.UnityIdmProperties;
import pl.edu.icm.unity.spring.saml.SamlSingleLogoutHandler;
import pl.edu.icm.unity.spring.slo.UnitySingleLogoutContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SingleLogoutHandler implements LogoutHandler {
    private final SamlSingleLogoutHandler singleLogoutHandler;
    private final UnitySingleLogoutContext singleLogoutContext;
    private final String singleLogoutEndpointUrl;

    public SingleLogoutHandler(SamlSingleLogoutHandler singleLogoutHandler,
                               UnitySingleLogoutContext singleLogoutContext,
                               UnityIdmProperties unityIdmProperties) {
        this.singleLogoutHandler = singleLogoutHandler;
        this.singleLogoutContext = singleLogoutContext;
        singleLogoutEndpointUrl = unityIdmProperties.getIdpSloUrl();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("Signing out, invalidating session: " + request.getSession().getId());

        final String principalCommonName = singleLogoutContext.getPrincipalCommonName();
        final String sessionIndex = singleLogoutContext.getSessionIndex();
        final X509Credential gridCredential = singleLogoutContext.getGridCredential();

        if (!singleLogoutHandler.performSingleLogoutRequest(response,
                singleLogoutEndpointUrl, principalCommonName, sessionIndex, gridCredential)) {
            final String redirectPath = "/";
            try {
                response.sendRedirect(redirectPath);
            } catch (java.io.IOException e) {
                log.error("Could not redirect to path: " + redirectPath);
            }
        }
    }

    private Log log = LogFactory.getLog(SingleLogoutHandler.class);
}

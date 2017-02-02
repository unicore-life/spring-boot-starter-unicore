package pl.edu.icm.unity.spring.authn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.edu.icm.unity.spring.api.IdentityProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SingleLogoutHandler implements LogoutHandler {
    @Autowired
    private SamlSingleLogoutHandler singleLogoutHandler;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("Invalidating session: " + request.getSession().getId());

//        TODO:
        final String singleLogoutEndpointUrl = null;
        final String principalCommonName = null;
        final String sessionIndex = null;
        IdentityProvider idProvider = null;

        if (!singleLogoutHandler.performSingleLogoutRequest(response,
                singleLogoutEndpointUrl, principalCommonName, sessionIndex, idProvider)) {
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

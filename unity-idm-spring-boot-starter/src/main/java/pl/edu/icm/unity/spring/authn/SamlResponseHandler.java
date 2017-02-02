package pl.edu.icm.unity.spring.authn;

import eu.unicore.samly2.SAMLBindings;
import eu.unicore.samly2.exceptions.SAMLValidationException;
import eu.unicore.samly2.trust.SamlTrustChecker;
import eu.unicore.samly2.trust.TruststoreBasedSamlTrustChecker;
import eu.unicore.samly2.validators.AssertionValidator;
import eu.unicore.samly2.validators.ReplayAttackChecker;
import eu.unicore.samly2.validators.SSOAuthnResponseValidator;
import eu.unicore.security.etd.TrustDelegation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import pl.edu.icm.unity.spring.api.IdentityProvider;
import xmlbeans.org.oasis.saml2.assertion.AssertionDocument;
import xmlbeans.org.oasis.saml2.assertion.AuthnStatementType;
import xmlbeans.org.oasis.saml2.protocol.ResponseDocument;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static eu.unicore.samly2.trust.CheckingMode.REQUIRE_SIGNED_RESPONSE_OR_ASSERTION;

@Component
class SamlResponseHandler {

    SamlResponseData processAuthenticationResponse(HttpServletRequest request,
                                                   String authenticationRequestId,
                                                   String targetUrl,
                                                   IdentityProvider identityProvider) {
        String samlResponse = request.getParameter("SAMLResponse");
        final SamlResponseData responseData = new SamlResponseData();
        try {
            ResponseDocument responseDocument = Utils.decodeMessage(samlResponse, log);
            final SSOAuthnResponseValidator validator =
                    validateSamlResponse(responseDocument, authenticationRequestId, targetUrl, identityProvider);

            final String sessionIndex = extractSessionIndex(validator);
            log.trace(String.format("Authority session index: %s", sessionIndex));
            responseData.setSessionIndex(sessionIndex);

            log.debug("Response document: " + responseDocument.xmlText());
            EtdAssertionsWrapper etdAssertionsWrapper = new EtdAssertionsWrapper(responseDocument);
            processAuthenticationResponseData(responseData, etdAssertionsWrapper);
            return responseData;
        } catch (Exception e) {
            String message = "Could not parse SAML authentication response!";
            log.error(message, e);
            throw new UnprocessableResponseException(message, e);
        }
    }

    private void processAuthenticationResponseData(SamlResponseData responseData,
                                                   EtdAssertionsWrapper etdAssertionsWrapper) {
        List<TrustDelegation> trustDelegationList = etdAssertionsWrapper
                .getEtdAssertions()
                .stream()
                .map(this::toTrustDelegation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (trustDelegationList.isEmpty()) {
            throw new RuntimeException("Missing trust delegation data!");
        }

        responseData.storeTrustDelegations(trustDelegationList);
        etdAssertionsWrapper.getAttributeData().getAttributes().forEach(
                (attributeKey, attributeValues) -> {
                    attributeValues
                            .forEach(value -> responseData.storeAttribute(attributeKey, value));
                }
        );
    }

    private SSOAuthnResponseValidator validateSamlResponse(ResponseDocument response,
                                                           String requestId,
                                                           String targetUrl,
                                                           IdentityProvider idProvider)
            throws URISyntaxException, SAMLValidationException {
        SamlTrustChecker trustChecker = new TruststoreBasedSamlTrustChecker(
                idProvider.getIdpValidator(),
                REQUIRE_SIGNED_RESPONSE_OR_ASSERTION
        );
        SSOAuthnResponseValidator validator = new SSOAuthnResponseValidator(
                idProvider.getGridCredential().getSubjectName(),
                new URI(targetUrl).toASCIIString(),
                requestId,
                AssertionValidator.DEFAULT_VALIDITY_GRACE_PERIOD,
                trustChecker,
                new ReplayAttackChecker(),
                SAMLBindings.HTTP_POST
        );
        validator.validate(response);
        return validator;
    }

    private String extractSessionIndex(SSOAuthnResponseValidator validator) {
        AuthnStatementType[] statements = validator
                .getAuthNAssertions()
                .get(0)
                .getAssertion()
                .getAuthnStatementArray();
        return statements.length > 0 ? statements[0].getSessionIndex() : "";
    }

    private TrustDelegation toTrustDelegation(AssertionDocument assertionDocument) {
        try {
            return new TrustDelegation(assertionDocument);
        } catch (Exception e) {
            log.warn("Problem with generating ETD", e);
            return null;
        }
    }

    private Log log = LogFactory.getLog(SamlResponseHandler.class);
}

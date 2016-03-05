package pl.edu.icm.unicore.spring.security;

import eu.unicore.security.etd.TrustDelegation;
import eu.unicore.util.httpclient.DefaultClientConfiguration;
import eu.unicore.util.httpclient.ETDClientSettings;
import eu.unicore.util.httpclient.IClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GridClientHelper {
    private final GridIdentityProvider identityProvider;

    @Autowired
    public GridClientHelper(GridIdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public IClientConfiguration createClientConfiguration(TrustDelegation trustDelegation) {
        DefaultClientConfiguration clientConfiguration = new DefaultClientConfiguration(
                identityProvider.getGridValidator(),
                identityProvider.getGridCredential()
        );
        ETDClientSettings etdSettings = clientConfiguration.getETDSettings();
        etdSettings.setTrustDelegationTokens(Arrays.asList(trustDelegation));
        etdSettings.setRequestedUser(trustDelegation.getCustodianDN());
        etdSettings.setExtendTrustDelegation(true);
        return clientConfiguration;
    }
}

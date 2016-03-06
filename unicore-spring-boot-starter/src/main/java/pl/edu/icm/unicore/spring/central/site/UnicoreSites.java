package pl.edu.icm.unicore.spring.central.site;

import de.fzj.unicore.uas.TargetSystemFactory;
import de.fzj.unicore.wsrflite.xmlbeans.client.RegistryClient;
import eu.unicore.security.etd.TrustDelegation;
import eu.unicore.util.httpclient.IClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3.x2005.x08.addressing.EndpointReferenceType;
import pl.edu.icm.unicore.spring.UnicoreProperties;
import pl.edu.icm.unicore.spring.util.GridClientHelper;

import java.util.List;
import java.util.stream.Collectors;

import static pl.edu.icm.unicore.spring.util.EndpointReferenceHelper.toEndpointReference;

@Repository
public class UnicoreSites {
    private final UnicoreProperties gridConfig;
    private final GridClientHelper clientHelper;

    @Autowired
    public UnicoreSites(UnicoreProperties gridConfig, GridClientHelper clientHelper) {
        this.gridConfig = gridConfig;
        this.clientHelper = clientHelper;
    }

    public List<UnicoreSiteEntity> getTargetSystemList(TrustDelegation trustDelegation)
            throws UnavailableSiteException {
        IClientConfiguration clientConfiguration = clientHelper.createClientConfiguration(trustDelegation);
        return collectTargetSystemServices(clientConfiguration);
    }

    public List<UnicoreSiteEntity> getTargetSystemList(IClientConfiguration clientConfiguration)
            throws UnavailableSiteException {
        return collectTargetSystemServices(clientConfiguration);
    }

    private List<UnicoreSiteEntity> collectTargetSystemServices(IClientConfiguration clientConfiguration)
            throws UnavailableSiteException {
        final String registryAddress = gridConfig.getRegistryUri();
        final EndpointReferenceType endpointReference = toEndpointReference(registryAddress);
        try {
            return new RegistryClient(endpointReference, clientConfiguration)
                    .listAccessibleServices(TargetSystemFactory.TSF_PORT)
                    .parallelStream()
                    .map(UnicoreSiteEntity::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(String.format(
                    "Error retrieving target systems from UNICORE registry <%s>", registryAddress), e);
            throw new UnavailableSiteException(e);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(UnicoreSites.class);
}

package pl.edu.icm.unicore.spring.central.tss;

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
import pl.edu.icm.unicore.spring.security.GridClientHelper;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UnicoreSites {
    private final UnicoreProperties gridConfig;
    private final GridClientHelper clientHelper;

    @Autowired
    public UnicoreSites(UnicoreProperties gridConfig, GridClientHelper clientHelper) {
        this.gridConfig = gridConfig;
        this.clientHelper = clientHelper;
    }

    public List<UnicoreSiteEntity> retrieveServiceList(TrustDelegation trustDelegation) {
        IClientConfiguration clientConfiguration = clientHelper.createClientConfiguration(trustDelegation);
        return collectTargetSystemList(clientConfiguration);
    }

    private List<UnicoreSiteEntity> collectTargetSystemList(IClientConfiguration clientConfiguration) {
        String registryUrl = gridConfig.getRegistryUri();
        EndpointReferenceType registryEpr = EndpointReferenceType.Factory.newInstance();
        registryEpr.addNewAddress().setStringValue(registryUrl);
        try {
            return new RegistryClient(registryEpr, clientConfiguration)
                    .listAccessibleServices(TargetSystemFactory.TSF_PORT)
                    .parallelStream()
                    .map(UnicoreSiteEntity::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(String.format("Error retrieving Target System from UNICORE Registry <%s>!", registryUrl), e);
            // TODO: should be used RuntimeException?
            throw new UnavailableSiteException(e);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(UnicoreSites.class);
}

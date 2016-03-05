package pl.edu.icm.unicore.spring.central.tss;

import de.fzj.unicore.uas.TargetSystemFactory;
import de.fzj.unicore.wsrflite.xmlbeans.client.RegistryClient;
import eu.unicore.security.etd.TrustDelegation;
import eu.unicore.util.httpclient.IClientConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3.x2005.x08.addressing.EndpointReferenceType;
import pl.edu.icm.unicore.spring.UnicoreProperties;
import pl.edu.icm.unicore.spring.security.GridClientHelper;

import java.util.List;
import java.util.stream.Collectors;

//@Repository
public class UnicoreSite {
    private final UnicoreProperties gridConfig;
    private final GridClientHelper clientHelper;

    @Autowired
    public UnicoreSite(UnicoreProperties gridConfig, GridClientHelper clientHelper) {
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
                    .map(endpointReferenceType -> new UnicoreSiteEntity(endpointReferenceType))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(String.format("Error retrieving Target System from UNICORE Registry <%s>!", registryUrl), e);
            // TODO: should be used RuntimeException?
            throw new UnavailableSiteException(e);
        }
    }

    private Log log = LogFactory.getLog(UnicoreSite.class);
}

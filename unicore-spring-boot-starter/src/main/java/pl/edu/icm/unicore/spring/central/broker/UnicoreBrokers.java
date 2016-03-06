package pl.edu.icm.unicore.spring.central.broker;

import de.fzj.unicore.wsrflite.xmlbeans.client.RegistryClient;
import eu.unicore.security.etd.TrustDelegation;
import eu.unicore.util.httpclient.IClientConfiguration;
import org.chemomentum.common.ws.IServiceOrchestrator;
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
public class UnicoreBrokers {
    private final UnicoreProperties unicoreProperties;
    private final GridClientHelper clientHelper;

    @Autowired
    public UnicoreBrokers(UnicoreProperties unicoreProperties, GridClientHelper clientHelper) {
        this.unicoreProperties = unicoreProperties;
        this.clientHelper = clientHelper;
    }

    public List<UnicoreBrokerEntity> getBrokerServices(TrustDelegation trustDelegation)
            throws UnavailableBrokerException {
        IClientConfiguration clientConfiguration = clientHelper.createClientConfiguration(trustDelegation);
        return collectBrokerServices(clientConfiguration);
    }

    public List<UnicoreBrokerEntity> getBrokerServices(IClientConfiguration clientConfiguration)
            throws UnavailableBrokerException {
        return collectBrokerServices(clientConfiguration);
    }

    private List<UnicoreBrokerEntity> collectBrokerServices(IClientConfiguration clientConfiguration)
            throws UnavailableBrokerException {
        final String registryAddress = unicoreProperties.getRegistryUri();
        final EndpointReferenceType endpointReference = toEndpointReference(registryAddress);
        try {
            return new RegistryClient(endpointReference, clientConfiguration)
                    .listAccessibleServices(IServiceOrchestrator.PORT)
                    .parallelStream()
                    .map(UnicoreBrokerEntity::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(String.format(
                    "Error retrieving orchestrator services from UNICORE registry <%s>!", registryAddress), e);
            throw new UnavailableBrokerException(e);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(UnicoreBrokers.class);
}

package pl.edu.icm.unicore.spring.central.factory;

import de.fzj.unicore.uas.StorageFactory;
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
public class UnicoreFactoryStorage {
    private final UnicoreProperties unicoreProperties;
    private final GridClientHelper clientHelper;

    @Autowired
    public UnicoreFactoryStorage(UnicoreProperties unicoreProperties, GridClientHelper clientHelper) {
        this.unicoreProperties = unicoreProperties;
        this.clientHelper = clientHelper;
    }

    public List<UnicoreFactoryStorageEntity> getFactoryStorageList(TrustDelegation trustDelegation)
            throws UnavailableFactoryStorageException {
        IClientConfiguration clientConfiguration = clientHelper.createClientConfiguration(trustDelegation);
        return collectFactoryStorageServices(clientConfiguration);
    }

    public List<UnicoreFactoryStorageEntity> getFactoryStorageList(IClientConfiguration clientConfiguration)
            throws UnavailableFactoryStorageException {
        return collectFactoryStorageServices(clientConfiguration);
    }

    private List<UnicoreFactoryStorageEntity> collectFactoryStorageServices(IClientConfiguration clientConfiguration)
            throws UnavailableFactoryStorageException {
        final String registryAddress = unicoreProperties.getRegistryUri();
        final EndpointReferenceType endpointReference = toEndpointReference(registryAddress);
        try {
            return new RegistryClient(endpointReference, clientConfiguration)
                    .listAccessibleServices(StorageFactory.SMF_PORT)
                    .parallelStream()
                    .map(UnicoreFactoryStorageEntity::new)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            logger.error(String.format(
                    "Error retrieving storage factories from UNICORE registry <%s>!", registryAddress), exception);
            throw new UnavailableFactoryStorageException(exception);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(UnicoreFactoryStorage.class);
}

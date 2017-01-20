package pl.edu.icm.unicore.spring.central.factory;

import org.w3.x2005.x08.addressing.EndpointReferenceType;

import java.io.Serializable;
import java.util.Objects;

public class UnicoreFactoryStorageEntity implements Serializable {
    private final EndpointReferenceType endpointReferenceType;

    public UnicoreFactoryStorageEntity(EndpointReferenceType endpointReferenceType) {
        this.endpointReferenceType = endpointReferenceType;
    }

    public EndpointReferenceType getEndpointReferenceType() {
        return endpointReferenceType;
    }

    public String getEndpointAddress() {
        return endpointReferenceType.getAddress().getStringValue();
    }

    @Override
    public String toString() {
        return String.format("UnicoreFactoryStorageEntity{endpointReferenceType=%s}", endpointReferenceType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UnicoreFactoryStorageEntity that = (UnicoreFactoryStorageEntity) other;
        return Objects.equals(endpointReferenceType, that.endpointReferenceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpointReferenceType);
    }
}

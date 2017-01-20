package pl.edu.icm.unicore.spring.central.site;

import org.w3.x2005.x08.addressing.EndpointReferenceType;

import java.io.Serializable;
import java.util.Objects;

public class UnicoreSiteEntity implements Serializable {
    private final EndpointReferenceType endpointReferenceType;

    public UnicoreSiteEntity(EndpointReferenceType endpointReferenceType) {
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
        return String.format("UnicoreSiteEntity{endpointReferenceType=%s}", endpointReferenceType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UnicoreSiteEntity that = (UnicoreSiteEntity) other;
        return Objects.equals(endpointReferenceType, that.endpointReferenceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpointReferenceType);
    }
}

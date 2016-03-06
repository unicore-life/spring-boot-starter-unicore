package pl.edu.icm.unicore.spring.central.broker;

import org.w3.x2005.x08.addressing.EndpointReferenceType;

import java.io.Serializable;
import java.util.Objects;

public class UnicoreBrokerEntity implements Serializable {
    private final String uri;

    public UnicoreBrokerEntity(EndpointReferenceType epr) {
        this.uri = epr.getAddress().getStringValue();
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return String.format("UnicoreBrokerEntity{uri='%s'}", uri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnicoreBrokerEntity that = (UnicoreBrokerEntity) o;
        return Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }
}

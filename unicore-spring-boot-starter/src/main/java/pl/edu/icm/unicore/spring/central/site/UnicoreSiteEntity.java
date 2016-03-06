package pl.edu.icm.unicore.spring.central.site;

import org.w3.x2005.x08.addressing.EndpointReferenceType;

import java.io.Serializable;
import java.util.Objects;

public class UnicoreSiteEntity implements Serializable {
    private final String uri;

    public UnicoreSiteEntity(EndpointReferenceType uri) {
        this.uri = uri.getAddress().getStringValue();
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return String.format("UnicoreSiteEntity{uri='%s'}", uri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnicoreSiteEntity that = (UnicoreSiteEntity) o;
        return Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }
}

package pl.edu.icm.unicore.spring.central.tss;

import org.w3.x2005.x08.addressing.EndpointReferenceType;

import java.io.Serializable;

public class UnicoreSiteEntity implements Serializable {
    private final String uri;

    public UnicoreSiteEntity(EndpointReferenceType uri) {
        this.uri = uri.getAddress().getStringValue();
    }

    public String getUri() {
        return uri;
    }
}

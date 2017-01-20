package pl.edu.icm.unicore.spring.util;

import org.w3.x2005.x08.addressing.EndpointReferenceType;

public final class EndpointReferenceHelper {

    /**
     * Creates EndpointReferenceType based on text address.
     *
     * @param address Text containing endpoint URI.
     * @return Address as EndpointReferenceType.
     */
    public static EndpointReferenceType toEndpointReference(String address) {
        EndpointReferenceType endpointReference = EndpointReferenceType.Factory.newInstance();
        endpointReference.addNewAddress().setStringValue(address);
        return endpointReference;
    }

    private EndpointReferenceHelper() {
    }
}

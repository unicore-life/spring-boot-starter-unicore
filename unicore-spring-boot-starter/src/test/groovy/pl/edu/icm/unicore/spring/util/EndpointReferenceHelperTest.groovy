package pl.edu.icm.unicore.spring.util

import spock.lang.Specification

import static pl.edu.icm.unicore.spring.util.EndpointReferenceHelper.toEndpointReference

class EndpointReferenceHelperTest extends Specification {

    def 'should convert string uri address'() {
        given:
        def uriAddress = 'https://server:0/SITE/service?res=XXX'

        when:
        def endpointReference = toEndpointReference(uriAddress)

        then:
        endpointReference.address.stringValue == uriAddress
    }
}

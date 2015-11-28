package pl.edu.icm.unicore

import spock.lang.Specification

class SomeTest extends Specification {

    def 'should create class'() {
        when:
        def some = new Some()

        then:
        some instanceof Some
    }
}

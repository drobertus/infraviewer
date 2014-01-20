package com.mechzombie.infraview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Enterprise)
class EnterpriseSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test creations"() {
        when:
        def ent1 = new Enterprise(name: 'testEnt1')
        then:
        assert ent1.name == 'testEnt1'
    }
}

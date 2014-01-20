package com.mechzombie.infraview

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SysadminController)
class SysadminControllerSpec extends Specification {

    def controller
    def entList = []

    def setup() {
        controller = new SysadminController()
        entList << new Enterprise(name: 'ent1')
        entList << new Enterprise(name: 'ent2')
    }

    def cleanup() {
    }

    void "test get list of enterprises unit"() {
        given:
        //def entMock = GroovyMock(Enterprise, global: true)
        //def entMock = MockFor(Enterprise)
        Enterprise.metaClass.static.getAll = { entList }
        when:

        println 'this is a test'

        println('defined a pair of enterprises')
        controller.getListOfEnterprises()
        def response = controller.response.text
        println "got response ${response}"

        then:

//        interaction{
//            1 * Enterprise.getAll >> entList
//        }

        assertTrue response.contains("\"name\":\"ent1\"")
        assertTrue response.contains("\"name\":\"ent2\"")
        //println "Response for airport/iata/foo: ${response}"
    }
}

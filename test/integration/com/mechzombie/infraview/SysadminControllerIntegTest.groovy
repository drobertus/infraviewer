package com.mechzombie.infraview



import spock.lang.*

/**
 *
 */
class SysadminControllerIntegTest extends GroovyTestCase {

    def controller = new SysadminController()
    def initialEntCount
    String entName = 'Bobs Enterprise'

    void setUp() {
        initialEntCount = Enterprise.count()
        new Enterprise(name: entName, activeDate: new Date()).save(flush: true)
        assert Enterprise.count == initialEntCount + 1
    }

    void tearDown() {
        def bobs = Enterprise.findByName(entName)
        bobs.delete(flush: true)
        assert initialEntCount == Enterprise.count()
    }
    void testGetListOfEnterprises() {

        println 'this is a test'

        controller.getListOfEnterprises()
        def response = controller.response.text
        println "got response : ${response}"

        assert response.contains("\"name\":\"Bobs Enterprise\"")
        println "Response for airport/iata/foo: ${response}"
    }
}

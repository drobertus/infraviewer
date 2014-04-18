import grails.test.AbstractCliTestCase

class EnterpriseDataLoadTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testEnterpriseDataLoad() {

        execute(["enterprise-data-load"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}

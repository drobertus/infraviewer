import com.mechzombie.infraview.Role
import com.mechzombie.infraview.User
import com.mechzombie.infraview.UserRole
import com.mechzombie.infraview.Enterprise
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
        def suRole = new Role(authority: 'ROLE_SUPERUSER').save(flush: true)
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
        //println 'init 2'
        def testUserBasic = new User(username: 'user', enabled: true, password: 'pass')
        def testUser = new User(username: 'admin', enabled: true, password: 'pass')
        def su = new User(username: 'su', enabled: true, password: 'pass')

        testUser.save(flush: true)
        testUserBasic.save(flush: true)
        su.save(flush: true)
        //println 'init 3'
        assert Role.count() == 3
        assert User.count() == 3

        //println 'init 3.1'
        UserRole.create( testUser, adminRole, true)
        UserRole.create( testUser, userRole, true )
        UserRole.create( testUserBasic, userRole, true)
        UserRole.create (su, suRole, true)
        UserRole.create (su, userRole, true)
        UserRole.create (su, adminRole, true)


        //println 'init 4'

        assert UserRole.count() == 6

        println 'saved new user admin:pass, and user:pass, su:pass'


        if (Environment.current == Environment.TEST ||
                Environment.current == Environment.DEVELOPMENT) {
            println "Test environment"
            //println "Executing BootStrapTest"
            def activeDt = new Date()
            def e1 = new Enterprise(name: 'City of Longmont', activeDate: activeDt).save(flush: true)
            def e2 = new Enterprise(name: 'Colorado DOT', activeDate: activeDt).save(flush: true)

            assert Enterprise.count() == 2
            //  new BootStrapTest().init()
            println "Finished BootStrapTest"

        }
    }
    def destroy = {
    }
}

import com.mechzombie.infraview.Role
import com.mechzombie.infraview.State
import com.mechzombie.infraview.User
import com.mechzombie.infraview.UserRole
import com.mechzombie.infraview.Enterprise
import com.mechzombie.infraview.AssetClass
import com.mechzombie.infraview.Location
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

                
        def suRole = new Role(authority: 'ROLE_SUPERUSER').save(flush: true)
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
        
        assert Role.count() == 3
        
        def activeDt = new Date()
        def rootEnterprise = new Enterprise(name: 'InfraView', activeDate: activeDt, location: new Location().save(flush:true)).save(flush: true)
        
        println "Test environment"

        def e1 = new Enterprise(name: 'City of Longmont', activeDate: activeDt, location: new Location().save()).save(flush: true)
        def e1forestry = new Enterprise(
            name: 'Forestry Department', activeDate: activeDt, location: new Location().save(), parentEnterprise: e1).save(flush: true)
        e1.addToChildEnterprises(e1forestry).save(flush:true)
        
        def e2 = new Enterprise(name: 'Colorado DOT', activeDate: activeDt, location: new Location().save()).save(flush: true)

        assert Enterprise.count() == 4
        //  new BootStrapTest().init()
        println "Finished BootStrapTest"

        //}
        
        //println 'init 2'
        def testUserBasic = new User(username: 'user@longmont.gov', enabled: true, password: 'pass', enterprise: e1)
        def forestryUser = new User(username: 'admin@forestry.gov', enabled: true, password: 'pass', enterprise: e1forestry).save(flush:true)
        def testUser = new User(username: 'admin@longmont.gov', enabled: true, password: 'pass', enterprise: e1)
        def su = new User(username: 'su@infraview.com', enabled: true, password: 'pass', enterprise: rootEnterprise)

        testUser.save(flush: true)
        testUserBasic.save(flush: true)
        su.save(flush: true)
        //println 'init 3'
        assert User.count() == 4
        
        UserRole.create( testUser, adminRole, true)
        UserRole.create( testUser, userRole, true )
        UserRole.create( forestryUser, adminRole, true)
        UserRole.create( forestryUser, userRole, true )
        UserRole.create( testUserBasic, userRole, true)
        UserRole.create (su, suRole, true)
        UserRole.create (su, userRole, true)
        UserRole.create (su, adminRole, true)


        
        assert UserRole.count() == 8

        println 'saved new user admin:pass, and user:pass, su:pass, forestry:pass'

        State.create("California", "CA", true)
        State.create("Texas", "TX", true)
        State.create("Colorado", "CO", true)
        State.create("Florida", "FL", true)
        State.create("New York", "NY", true)
        
        println 'creating asset class'
        
        def hydrants = new AssetClass(name:'Hydrant', enterprise: e1, 
            description: 'Fire hydrant',
            statusValueNew: 10.0,
            statusValueReplace: 7.0,
            statusValueDestroyed: 0.0,
            expectedLifeSpanYears: 35.0,
            standardInspectionInterval: 2.0,
            standardMaintenanceInterval: 5.0).save(flush: true)
    }
    
    def destroy = {
    }
}

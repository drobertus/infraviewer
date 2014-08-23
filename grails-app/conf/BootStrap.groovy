import com.mechzombie.infraview.*
import grails.util.Environment
//import groovy.sql.Sql

class BootStrap {

    //def dataSource
    
    def init = { servletContext ->

    

//        try {
//            def sql = new Sql(dataSource)
//            sql.executeUpdate('CREATE ALIAS InitGeoDB for "geodb.GeoDB.InitGeoDB"')
//            sql.executeUpdate('CALL InitGeoDB()')
//        } catch (java.sql.SQLException e) {
//            log.debug('', e)
//        }
//        
                
        def suRole = new Role(authority: 'ROLE_SUPERUSER').save(flush: true)
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
        
        assert Role.count() == 3
        
        def activeDt = new Date()
        def rootEnterprise = new Enterprise(name: 'InfraView', activeDate: activeDt, location: new Location().save(flush:true)).save(flush: true)
        assert Enterprise.count() == 1
        println "Test environment"

        def e1 = new Enterprise(name: 'City of Longmont', activeDate: activeDt, location: new Location().save()).save(flush: true)
        def e1forestry = new Enterprise(
            name: 'Forestry Department', activeDate: activeDt, location: new Location().save(), parentEnterprise: e1).save(flush: true)
        e1.addToChildEnterprises(e1forestry).save(flush:true)
        
        def e2 = new Enterprise(name: 'Colorado DOT', activeDate: activeDt, location: new Location().save()).save(flush: true)

        assert Enterprise.count() == 4
        //1 new BootStrapTest().init()
        println "Finished BootStrapTest"

        //}
        
        //println 'init 2'
        def testUserBasic = new InfraUser(username: 'user@longmont.gov', enabled: true, password: 'pass', enterprise: e1)
        def forestryUser = new InfraUser(username: 'admin@forestry.gov', enabled: true, password: 'pass', enterprise: e1forestry).save(flush:true)
        def testUser = new InfraUser(username: 'admin@longmont.gov', enabled: true, password: 'pass', enterprise: e1)
        def su = new InfraUser(username: 'su@infraview.com', enabled: true, password: 'pass', enterprise: rootEnterprise)

        testUser.save(flush: true)
        testUserBasic.save(flush: true)
        su.save(flush: true)
        //println 'init 3'
        assert InfraUser.count() == 4
        
        InfraUserRole.create( testUser, adminRole, true)
        InfraUserRole.create( testUser, userRole, true )
        InfraUserRole.create( forestryUser, adminRole, true)
        InfraUserRole.create( forestryUser, userRole, true )
        InfraUserRole.create( testUserBasic, userRole, true)
        InfraUserRole.create (su, suRole, true)
        InfraUserRole.create (su, userRole, true)
        InfraUserRole.create (su, adminRole, true)


        
        assert InfraUserRole.count() == 8

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
            standardMaintenanceInterval: 5.0,
            assetHasAddress: false,
            assetHasLocation: true,
            assetGeometryType: Geometry.GeometryType.POINT,
            organic: false,
            assetClassCategory: "Water").save(flush: true)
        
        
        assert AssetClass.count() == 1
        
        //add a report
        def startDate = new Date()
        def endDate = startDate.plus(365)
        def hydRpt = new Report(enterprise: e1, title: "Hydrant replacement",
            reportType: ReportType.AssetReplacementProjection ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate).save(flush: true)
    
        assert Report.count() == 1
    }
    
    def destroy = {
    }
}

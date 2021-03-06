package com.mechzombie.infraview

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.*
import org.junit.Before
import spock.lang.*

@TestFor(InfraUserController)
@Mock([InfraUser, Enterprise, InfraUserRole, Role, Location])
class InfraUserControllerSpec extends Specification {

    @Before
    void setup() {
        InfraUser.metaClass.encodePassword = { -> }
    }
    
    def populateValidParams(params) {
        //setup:
        assert params != null

        def testEnt = new Enterprise(name: 'City of Test', 
            activeDate: new Date(), 
            location: new Location(hasAddress: false).save(flush: true))
            .save(flush: true)
            
        
        println("test ent = ${testEnt.id}")
        params["username"] = "someValidName@test.com"
        
        params["enterprise"] = testEnt.id 
        params["enabled"] = true
        params["password"] = "pass"
        params["accountExpired"] = false
        params["passwordExpired"] = false
        params["accountLocked"] = false
        params["authorities"] = ""
    
        println("user conteroller save params = " + params)
     }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.infraUserInstanceList
            model.infraUserInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        setup:
        
            def loggedInUser = Mock(InfraUser)
            def suRole = new Role(authority: 'ROLE_SUPERUSER')
            def adminRole = new Role(authority: 'ROLE_ADMIN')
            def userRole = new Role(authority: 'ROLE_USER')
        
            def auths = [suRole, adminRole, userRole]
            loggedInUser.authorities >> auths
            //  new User(username: "Bob").save() // This way the user will have an ID
            controller.springSecurityService = [
            encodePassword: 'password',
            reauthenticate: { String u -> true},
            loggedIn: true,
            principal: loggedInUser]

            Role.metaClass.findAllByAuthorityNotEqual('ROLE_SUPERUSER') >>
             [adminRole, userRole]
        
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.infraUserInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.method = 'POST'
            request.format='form'
            def user = new InfraUser()
            user.validate()
            println("user errors invlaid= ${user.errors}")
            controller.save(user)

        then:"The create view is rendered again with the correct model"
            println("model=" + model )
            model.infraUserInstance != null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            request.method = 'POST'
            request.format='form'
            populateValidParams(params)
            user = new InfraUser(params)
            //user.springSecurityService = new SpringSecurityService() 
            user.validate()
            println("user errors= ${user.errors}")
            controller.save(user)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/infraUser/show/1'
            controller.flash.message != null
            InfraUser.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def user = new InfraUser(params)
            controller.show(user)

        then:"A model is populated containing the domain instance"
            model.infraUserInstance == user
    }

    void "Test that the edit action returns the correct model"() {
                setup:
        
            def loggedInUser = Mock(InfraUser)
            def suRole = new Role(authority: 'ROLE_SUPERUSER')
            def adminRole = new Role(authority: 'ROLE_ADMIN')
            def userRole = new Role(authority: 'ROLE_USER')
        
            def auths = [suRole, adminRole, userRole]
            loggedInUser.authorities >> auths
            //  new User(username: "Bob").save() // This way the user will have an ID
            controller.springSecurityService = [
            encodePassword: 'password',
            reauthenticate: { String u -> true},
            loggedIn: true,
            principal: loggedInUser]

            Role.metaClass.findAllByAuthorityNotEqual('ROLE_SUPERUSER') >>
             [adminRole, userRole]
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def user = new InfraUser(params)
            controller.edit(user)

        then:"A model is populated containing the domain instance"
            model.infraUserInstance == user
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.method = 'PUT'
            request.format='form'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/infraUser/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            request.method = 'PUT'
            request.format='form'
            def user = new InfraUser()
            user.validate()
            controller.update(user)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.infraUserInstance == user

        when:"A valid domain instance is passed to the update action"
            response.reset()
            request.method = 'PUT'
            request.format='form'
            populateValidParams(params)
            user = new InfraUser(params)
            //user.springSecurityService = new SpringSecurityService() 
            user.save(flush: true)
            controller.update(user)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/infraUser/show/$user.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.method = 'DELETE'
            request.format='form'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/infraUser/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def user = new InfraUser(params)
            //user.springSecurityService = new SpringSecurityService() 
            user.save(flush: true)

        then:"It exists"
            InfraUser.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(user)

        then:"The instance is deleted"
            InfraUser.count() == 0
            response.redirectedUrl == '/infraUser/index'
            flash.message != null
    }
}

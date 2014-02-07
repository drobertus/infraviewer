package com.mechzombie.infraview



import grails.test.mixin.*

import spock.lang.Specification
//import spock.lang.*

@TestFor(EnterpriseController)
@Mock(Enterprise)
class EnterpriseControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params["name"] = 'ent1'
        params["activeDate"] = new Date()
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.enterpriseInstanceList
            model.enterpriseInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.enterpriseInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def enterprise = new Enterprise()
            enterprise.validate()
            controller.save(enterprise)

        then:"The create view is rendered again with the correct model"
            model.enterpriseInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            enterprise = new Enterprise(params)

            controller.save(enterprise)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/enterprise/show/1'
            controller.flash.message != null
            Enterprise.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def enterprise = new Enterprise(params)
            controller.show(enterprise)

        then:"A model is populated containing the domain instance"
            model.enterpriseInstance == enterprise
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def enterprise = new Enterprise(params)
            controller.edit(enterprise)

        then:"A model is populated containing the domain instance"
            model.enterpriseInstance == enterprise
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/enterprise/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def enterprise = new Enterprise()
            enterprise.validate()
            controller.update(enterprise)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.enterpriseInstance == enterprise

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            enterprise = new Enterprise(params).save(flush: true)
            controller.update(enterprise)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/enterprise/show/$enterprise.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/enterprise/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def enterprise = new Enterprise(params).save(flush: true)

        then:"It exists"
            Enterprise.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(enterprise)

        then:"The instance is deleted"
            Enterprise.count() == 0
            response.redirectedUrl == '/enterprise/index'
            flash.message != null
    }
}

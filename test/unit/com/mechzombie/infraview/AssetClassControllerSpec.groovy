package com.mechzombie.infraview



import grails.test.mixin.*
import spock.lang.*

@TestFor(AssetClassController)
@Mock([AssetClass, Enterprise, Asset])
class AssetClassControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
 
        def testEnt = new Enterprise(name: 'City of Longmont', activeDate: new Date()).save(flush: true)
        
        params["name"] = 'hydrant'
        params["statusValueNew"] = "10"
        params["statusValueReplace"] = "5"
        params["statusValueDestroyed"] = "0"
        params["expectedLifeSpanYears"] = "35"
        params["standardInspectionInterval"] = "1"
        params["standardMaintenanceInterval"] = "3"
        params["enterprise"] = testEnt

    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.assetClassInstanceList
            model.assetClassInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.assetClassInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def assetClass = new AssetClass()
            assetClass.validate()
            controller.save(assetClass)

        then:"The create view is rendered again with the correct model"
            model.assetClassInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            assetClass = new AssetClass(params)
            //println("the name =" + assetClass.name)
            controller.save(assetClass)

        then:"A redirect is issued to the show action"
            //println("errors= ${assetClass.errors}")
            response.redirectedUrl == '/assetClass/show/1'
            controller.flash.message != null
            AssetClass.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def assetClass = new AssetClass(params)
            controller.show(assetClass)

        then:"A model is populated containing the domain instance"
            model.assetClassInstance == assetClass
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def assetClass = new AssetClass(params)
            controller.edit(assetClass)

        then:"A model is populated containing the domain instance"
            model.assetClassInstance == assetClass
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/assetClass/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def assetClass = new AssetClass()
            assetClass.validate()
            controller.update(assetClass)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.assetClassInstance == assetClass

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            assetClass = new AssetClass(params).save(flush: true)
            controller.update(assetClass)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/assetClass/show/$assetClass.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/assetClass/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def assetClass = new AssetClass(params).save(flush: true)

        then:"It exists"
            AssetClass.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(assetClass)

        then:"The instance is deleted"
            AssetClass.count() == 0
            response.redirectedUrl == '/assetClass/index'
            flash.message != null
    }
}

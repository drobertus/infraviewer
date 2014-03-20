package com.mechzombie.infraview



import grails.test.mixin.*
import spock.lang.*

@TestFor(AssetController)
@Mock([Asset, AssetClass, Enterprise, Location])
class AssetControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
         def testAC = makeAssetClass()
    //static hasMany = [assets: Asset]
        params['assetClass'] = testAC
        params['externalId'] = 'HYD0032'
        params['description'] = 'NA'
        params['notes'] = '3 plugs'
        params['location'] = new Location();
        params['assetClass.id'] = 12
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.assetInstanceList
            model.assetInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            def testAC = makeAssetClass()
            params['assetClass.id'] = testAC.id
            controller.create()

        then:"The model is correctly created"
            model.assetInstance!= null
            model.assetInstance.assetClass.id != null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def asset = new Asset()
            asset.validate()
            controller.save(asset)

        then:"The create view is rendered again with the correct model"
            model.assetInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            asset = new Asset(params)

            controller.save(asset)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/asset/show/1'
            controller.flash.message != null
            Asset.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def asset = new Asset(params)
            controller.show(asset)

        then:"A model is populated containing the domain instance"
            model.assetInstance == asset
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def asset = new Asset(params)
            controller.edit(asset)

        then:"A model is populated containing the domain instance"
            model.assetInstance == asset
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/asset/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def asset = new Asset()
            asset.validate()
            controller.update(asset)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.assetInstance == asset

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            asset = new Asset(params).save(flush: true)
            controller.update(asset)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/asset/show/$asset.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/asset/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def asset = new Asset(params).save(flush: true)

        then:"It exists"
            Asset.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(asset)

        then:"The instance is deleted"
            Asset.count() == 0
            response.redirectedUrl == '/asset/index'
            flash.message != null
    }
    
    def makeAssetClass() {
        
        def testEnt = new Enterprise(name: 'testEnt', activeDate: new Date())
        testEnt.save(flush: true)
       def testAC = new AssetClass(name: 'hydrant', 
            statusValueNew: 10.0,
             statusValueReplace: 7,
             statusValueDestroyed: 0,
             expectedLifeSpanYears: 30,
             standardInspectionInterval: 1,
             standardMaintenanceInterval: 5,
             enterprise: testEnt)
        testAC.validate()
         //println "errors =" + testAC.getErrors()
        
        
        testAC.save(flush: true) 
        // log.info("testAC id = ${testAC.id}")
        
        return testAC
    }
}

package com.mechzombie.infraview



import grails.test.mixin.*
import spock.lang.*

@TestFor(WorkOrderController)
@Mock(WorkOrder)
class WorkOrderControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        
//            Enterprise enterprise    
//    Location location
//    Date reportedDate
//    Date scheduledWorkDate
//    String reportedBy
//    String notes
//    InfraUser projectManager
//    String workOrderId
//    Date completedDate    
//    InfraUser certifiedCompleteBy
//    AssetStatusEventType workType
//    AssetClass assetClass
//    Asset asset
//    WorkOrderStatusType status
//    StoredFileSet imageSet
//    
//    static hasMany = [assignedStaff: InfraUser]
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.workOrderInstanceList
            model.workOrderInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.workOrderInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def workOrder = new WorkOrder()
            workOrder.validate()
            controller.save(workOrder)

        then:"The create view is rendered again with the correct model"
            model.workOrderInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            workOrder = new WorkOrder(params)

            controller.save(workOrder)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/workOrder/show/1'
            controller.flash.message != null
            WorkOrder.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def workOrder = new WorkOrder(params)
            controller.show(workOrder)

        then:"A model is populated containing the domain instance"
            model.workOrderInstance == workOrder
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def workOrder = new WorkOrder(params)
            controller.edit(workOrder)

        then:"A model is populated containing the domain instance"
            model.workOrderInstance == workOrder
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/workOrder/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def workOrder = new WorkOrder()
            workOrder.validate()
            controller.update(workOrder)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.workOrderInstance == workOrder

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            workOrder = new WorkOrder(params).save(flush: true)
            controller.update(workOrder)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/workOrder/show/$workOrder.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/workOrder/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def workOrder = new WorkOrder(params).save(flush: true)

        then:"It exists"
            WorkOrder.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(workOrder)

        then:"The instance is deleted"
            WorkOrder.count() == 0
            response.redirectedUrl == '/workOrder/index'
            flash.message != null
    }
}

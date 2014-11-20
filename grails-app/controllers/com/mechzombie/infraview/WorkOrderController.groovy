package com.mechzombie.infraview



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WorkOrderController {

    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        println("index=${max}" )
        //def theUser = InfraUser.findByUsername(principal.username)
        def activeEnterprise = session['activeEnterprise']
        def books = WorkOrder.findAllWhere(enterprise: activeEnterprise)
        params.max = Math.min(max ?: 10, 100)
        respond WorkOrder.list(params), model:[workOrderInstanceCount: WorkOrder.count()]
    }
    
    def show(WorkOrder workOrderInstance) {
        println "show Work order called -> ${workOrderInstance}"
        respond workOrderInstance
    }

    def create() {
        println "createWork order called -> ${params}"
        //params['eventType'] = WorkOrderStatusType.Incomplete
        respond new WorkOrder(eventType: WorkOrderStatusType.Unscheduled, 
            enterprise: session['activeEnterprise'],
            reportedDate: new Date(),
            completedDate: null,
            projectManager: null,
            reportedBy: principal.username
        )
    
    }

    @Transactional
    def save(WorkOrder workOrderInstance) {
        if (workOrderInstance == null) {
            notFound()
            return
        }

        if (workOrderInstance.hasErrors()) {
            respond workOrderInstance.errors, view:'create'
            return
        }

        workOrderInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workOrder.label', default: 'WorkOrder'), workOrderInstance.id])
                redirect workOrderInstance
            }
            '*' { respond workOrderInstance, [status: CREATED] }
        }
    }

    def edit(WorkOrder workOrderInstance) {
        respond workOrderInstance
    }

    @Transactional
    def update(WorkOrder workOrderInstance) {
        if (workOrderInstance == null) {
            notFound()
            return
        }

        if (workOrderInstance.hasErrors()) {
            respond workOrderInstance.errors, view:'edit'
            return
        }

        workOrderInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'WorkOrder.label', default: 'WorkOrder'), workOrderInstance.id])
                redirect workOrderInstance
            }
            '*'{ respond workOrderInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(WorkOrder workOrderInstance) {

        if (workOrderInstance == null) {
            notFound()
            return
        }

        workOrderInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'WorkOrder.label', default: 'WorkOrder'), workOrderInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workOrder.label', default: 'WorkOrder'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

package com.mechzombie.infraview


import grails.plugins.springsecurity.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true )
class EnterpriseController {

    def springSecurityService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    @Secured(['ROLE_SUPERUSER'])
    def index(Integer max) {
        log.info "Showing list of ents"
        params.max = Math.min(max ?: 10, 100)
        //println("shoping enterprise ${enterpriseInstance.name}")
        respond Enterprise.list(params), model:[enterpriseInstanceCount: Enterprise.count()]
    }

    //@Secured(['ROLE_ADMIN'])
    def show(Enterprise enterpriseInstance) {
        println("showing enterprise ${enterpriseInstance?.name}")
        //def roleNames = principal.authorities*.authority
        //if (roleNames.contains('ROLE_SUPERUSER')) {
        session['activeEnterprise'] = enterpriseInstance
        //}
        respond enterpriseInstance
    }

    @Secured(['ROLE_SUPERUSER'])
    def create() {
        respond new Enterprise(params)
    }
    
    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    @Transactional
    def save(Enterprise enterpriseInstance) {
        if (enterpriseInstance == null) {
            notFound()
            return
        }

        if (enterpriseInstance.hasErrors()) {
            respond enterpriseInstance.errors, view:'create'
            return
        }

        enterpriseInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'enterpriseInstance.label', default: 'Enterprise'), enterpriseInstance.id])
                redirect enterpriseInstance
            }
            '*' { respond enterpriseInstance, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Enterprise enterpriseInstance) {
        respond enterpriseInstance
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def update(Enterprise enterpriseInstance) {
        println "params =${params}"
        if (enterpriseInstance == null) {
            notFound()
            return
        }

        if (enterpriseInstance.hasErrors()) {
            respond enterpriseInstance.errors, view:'edit'
            return
        }
              
        buildAddress(enterpriseInstance, params)
        if (enterpriseInstance?.location?.address?.hasErrors()) {
            println("found errors ${enterpriseInstance.location.address.errors}")
            //enterpriseInstance.errors << enterpriseInstance.location.address.errors
            respond enterpriseInstance.errors, view: 'edit'
            return;
        }
        
        enterpriseInstance.save flush:true
        
        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Enterprise.label', default: 'Enterprise'), enterpriseInstance.id])
                redirect enterpriseInstance
            }
            '*'{ respond enterpriseInstance, [status: OK] }
        }
    }

    @Secured(['ROLE_SUPERUSER'])
    @Transactional
    def delete(Enterprise enterpriseInstance) {

        if (enterpriseInstance == null) {
            notFound()
            return
        }

        enterpriseInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Enterprise.label', default: 'Enterprise'), enterpriseInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'enterpriseInstance.label', default: 'Enterprise'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def buildAddress(Enterprise ent, def params) {
        
        if (!ent.hasAddress) {
            // TODO: if there is a location delete the address reference
            def entLoc = ent.location
            if(entLoc) {
                entLoc.address = null;
                entLoc.save flush: true
            }
            return
        }

        def theState = State.get(params.state)
        def updateAdd = Address.findOrSaveWhere(addressLine1: params.addressLine1,
            addressLine2: params.addressLine2, city: params.city, state: theState,
            postalCode: params.postalCode)

        def newLoc = ent.location
        if(!newLoc) {
            newLoc = new Location()
            newLoc.save(flush: true)//, failOnError:true) //, insert: true)
            println("newLoc id = ${newLoc.id}")
            ent.location = newLoc
        }
        newLoc.address = updateAdd
    }
}

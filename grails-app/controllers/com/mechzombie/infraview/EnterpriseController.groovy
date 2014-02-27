package com.mechzombie.infraview


import grails.plugins.springsecurity.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true )
class EnterpriseController {

    //def springSecurityService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    @Secured(['ROLE_SUPERUSER'])
    def index(Integer max) {
        log.info "Showing list of ents"
        params.max = Math.min(max ?: 10, 100)
        //println("shoping enterprise ${enterpriseInstance.name}")
        respond Enterprise.list(params), model:[enterpriseInstanceCount: Enterprise.count()]
    }

    @Secured(['ROLE_ADMIN'])
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
       
        setAddress(enterpriseInstance, params)
        enterpriseInstance.save flush:true
        println "address count=" + Address.count()
        
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

    def setAddress(Enterprise ent, def params) {
        
        println('creating address params = ' + params)
        println('creating address enterprise = ' + ent)
        if(!ent.location) {
            def address = new Address(params)
        if (address.hasErrors()) {
            println "address errors = " + address.errors
        }
            
            println("address ln1=" + address.addressLine1)
            println("address state=" + address.state)
            address.save(flush: true, failOnError: true)
             println "add save errors=" + address.errors.allErrors
//, insert: true)
//            if (!address.isAttached()){
//                address.attach()
//            }
            println("add id=" + address.id)
            def newLoc = new Location(address: address, geometry: null)       
            newLoc.save(flush: true, failOnError:true) //, insert: true)
//            if (!newLoc.isAttached()){
//                newLoc.attach()
//            }
            println("newLoc id = ${newLoc.id}")
            println("newLoc address = ${newLoc.address}")
            println("newLoc=" + newLoc)
            ent.location = newLoc
            
        }
        else {
            println 'updateing exisitng address'
            def theAdd = ent.location.address
            theAdd.update(params, flush: true)
        }
        
    }
}

package com.mechzombie.infraview


import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true )
class EnterpriseController {

    def springSecurityService
    def addressHandlingService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    @Secured(['ROLE_SUPERUSER'])
    def index(Integer max) {
        log.info "Showing list of ents"
        params.max = Math.min(max ?: 10, 100)
        respond Enterprise.list(params), model:[enterpriseInstanceCount: Enterprise.count()]
    }

    @Secured(['ROLE_USER'])
    def show(Enterprise enterpriseInstance) {
        println("showing enterprise ${enterpriseInstance?.name}")
        session['activeEnterprise'] = enterpriseInstance
        respond enterpriseInstance
    }

    @Secured(['ROLE_SUPERUSER'])
    def create() {
        respond new Enterprise(params)
    }
    
    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    @Transactional
    def save(Enterprise enterpriseInstance) {
        //log.info 'the save is attempted'
        //println('enterprise save params = ' + params)
        if (enterpriseInstance == null) {
            notFound()
            return
        }
        //println(' ent save 1')
        def theLoc
        if (!enterpriseInstance.location) {
          //  println(' ent save 2')
            theLoc = new Location(hasAddress: (params['_hasAddress'] != null))            
            theLoc.validate()
            if (theLoc.hasErrors()) {
                println("newLoc Errors" + theLoc.getErrors())
            }
            theLoc.save(flush:true)
           enterpriseInstance.location = theLoc
        }
        //println( 'ent save 3')
        enterpriseInstance.validate()
        if (enterpriseInstance.hasErrors()) {
          //  println(' ent errors = ' + enterpriseInstance.errors)
            respond enterpriseInstance.errors, view:'create'
            return
        }
//println( 'ent save 4')
        addressHandlingService.buildAddress(enterpriseInstance.location, params)
        if (enterpriseInstance?.location?.address?.hasErrors()) {
            //println("found errors ${enterpriseInstance.location.address.errors}")
            //enterpriseInstance.errors << enterpriseInstance.location.address.errors
            respond enterpriseInstance.location.address.errors, view: 'create'
            return;
        }
        
  //      println("new ent = " + enterpriseInstance)
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
        //println "params =${params}"
        if (enterpriseInstance == null) {
            notFound()
            return
        }

        
        if (enterpriseInstance.hasErrors()) {
          //  println("errors are: ${enterpriseInstance.getErrors()}")
            respond enterpriseInstance.errors, view:'edit'
            return
        }
        addressHandlingService.buildAddress(enterpriseInstance.location, params)
        if (enterpriseInstance?.location?.address?.hasErrors()) {
            //println("found errors ${enterpriseInstance.location.address.errors}")
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
}

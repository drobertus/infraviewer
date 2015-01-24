package com.mechzombie.infraview


import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.web.multipart.commons.CommonsMultipartFile

@Transactional(readOnly = true )
class EnterpriseController {

    def springSecurityService
    def addressHandlingService
    def fileService
    
    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]
    
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
        enterpriseInstance.validate()
        if (enterpriseInstance.hasErrors()) {        
            respond enterpriseInstance.errors, view:'create'
            return
        }

        addressHandlingService.buildAddress(enterpriseInstance.location, params)
        if (enterpriseInstance?.location?.address?.hasErrors()) {
            respond enterpriseInstance.location.address.errors, view: 'create'
            return;
        }
        enterpriseInstance.save flush:true
        uploadLogo params, enterpriseInstance.id

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'enterpriseInstance.label', default: 'Enterprise'), enterpriseInstance.id])
                redirect enterpriseInstance
            }
            '*' { respond enterpriseInstance, [view: 'show', status: CREATED] }
        }
    }

    def displayLogo() {
        def theEnt = session['activeEnterprise']       
        def img = fileService.getCurrentEnterpriseLogo(theEnt.id)

        if (img) {
           
            response.setHeader('Content-length', "${img.length}")
            response.contentType = 'image/jpg' // or the appropriate image content type
            response.outputStream << img
            response.outputStream.flush()
        }
    }
    
    @Secured(['ROLE_ADMIN'])
    def edit(Enterprise enterpriseInstance) {
        respond enterpriseInstance
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def update(Enterprise enterpriseInstance) {
        //println "update params =${params}"
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
        uploadLogo params, enterpriseInstance.id
        
        request.withFormat {
            form {              
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Enterprise.label', default: 'Enterprise'), enterpriseInstance.id])
                redirect enterpriseInstance
            }            
            '*'{ respond enterpriseInstance, [view:'show', status: OK] }
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

    private void uploadLogo(params, theEnt){

        CommonsMultipartFile f = params['logoImage']        
        if (f) {           
            if (f.bytes) {
                println("updating logo for ${theEnt} with ${f.originalFilename}")
                fileService.uploadEnterpriseLogo(f.bytes, f.originalFilename, theEnt)    
            }
        }
        else {
            println 'no logoImage section found'
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

package com.mechzombie.infraview


import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AssetController {

    def springSecurityService
    def addressHandlingService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_USER']) 
    def index(Integer max) {
        
        //TODO: this needs to bring in an asset Class Id
        //do we stash the "active" assetClass in the session for convenience?
        //(also check that the active user belongs to the same eneterprise as the asset class
        // for security purposed)
        //println ('create params = ' + params)
        def assetClassId = params["assetClass.id"]
        //println('assetclass id =[' + assetClassId + "]")
        def assetClass = AssetClass.get( assetClassId )
        //println "assetClass = ${assetClass.name}"
       // assertNotNull assetClass
        params.max = Math.min(max ?: 10, 100)
        respond Asset.list(params), model:[assetInstanceCount: Asset.count(), assetClass: assetClass]
    }

    @Secured(['ROLE_USER'])
    def show(Asset assetInstance) {
        respond assetInstance
    }

    @Secured(['ROLE_USER'])
    def create(AssetClass assetClass) {
        println 'create params=' + params
        def asset = new Asset(params)
        
        def assetClassId = params["assetClass.id"]
        
        def assetClass1 = AssetClass.get(assetClassId )
       
        asset.assetClass  = assetClass1
        respond asset 
    }

    @Secured(['ROLE_USER'])
    @Transactional
    def save(Asset assetInstance) {
        
        //println('save params=' + params)
        if (assetInstance == null) {
           // println('returning null')
            notFound()
            return
        }

        //TODO: we only want to create a Location when the 
        //asset is going to be saved
        if(!assetInstance.location) {
            //println "the params befoer save are" + params
            assetInstance.location = new Location(params)
            assetInstance.location.validate()
            //println "the params befoer fail are" + params
            if(assetInstance.location.hasErrors()){
                // println "the location errors are " +  assetInstance.location.errors
                // println "the asset errors are " +  assetInstance.errors
                 params["assetClass.id"] = assetInstance.assetClass.id
                 
                 params["assetInstance"] = assetInstance
                respond assetInstance.location.errors, view: 'create', model:params
                return
            }
            else
            {
                assetInstance.location.save(flush: true)
              //  println "new location id = ${assetInstance.location.id}"                
            }
            
        }
        
        addressHandlingService.buildAddress(assetInstance.location, params)
        if (assetInstance?.location?.address?.hasErrors()) {
            //println("found errors ${enterpriseInstance.location.address.errors}")
            //enterpriseInstance.errors << enterpriseInstance.location.address.errors
            respond assetInstance.location.address.errors, view: 'create'
            return;
        }
        assetInstance.validate()
        
        if (assetInstance.hasErrors()) {
          //  println("errors found ${assetInstance.getErrors()}")
            respond assetInstance.errors, view:'create'
            return
        }
    
        //println(" assetClass id = " + params['assetClass.id'])
        
        assetInstance.save flush:true
        //println("errors found post  save: ${assetInstance.getErrors()}")
       
        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assetInstance.label', default: 'Asset'), assetInstance.externalId])
                redirect assetInstance
            }
            '*' { respond assetInstance, [status: CREATED] }
        }
    }

    @Secured(['ROLE_USER'])
    def edit(Asset assetInstance) {
        respond assetInstance
    }

    @Secured(['ROLE_USER'])
    @Transactional
    def update(Asset assetInstance) {
        if (assetInstance == null) {
            notFound()
            return
        }

        if (assetInstance.hasErrors()) {
            respond assetInstance.errors, view:'edit'
            return
        }

        addressHandlingService.buildAddress(assetInstance.location, params)
        if (assetInstance?.location?.address?.hasErrors()) {
            //println("found errors ${enterpriseInstance.location.address.errors}")
            //enterpriseInstance.errors << enterpriseInstance.location.address.errors
            respond assetInstance.location.address.errors, view: 'edit'
            return;
        }
        
        assetInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Asset.label', default: 'Asset'), assetInstance.externalId])
                redirect assetInstance
            }
            '*'{ respond assetInstance, [status: OK] }
        }
    }

    @Secured(['ROLE_USER'])
    @Transactional
    def delete(Asset assetInstance) {

        if (assetInstance == null) {
            notFound()
            return
        }

        assetInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Asset.label', default: 'Asset'), assetInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'assetInstance.label', default: 'Asset'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
    
     
}

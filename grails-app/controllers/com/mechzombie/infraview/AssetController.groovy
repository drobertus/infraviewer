package com.mechzombie.infraview


import grails.plugins.springsecurity.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AssetController {

    def springSecurityService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_USER']) 
    def index(Integer max) {
        
        //TODO: this needs to bring in an asset Class Id
        //do we stash the "active" assetClass in the session for convenience?
        //(also check that the active user belongs to the same eneterprise as the asset class
        // for security purposed)
        println ('create params = ' + params)
        def assetClass = AssetClass.get(params['assetClass.id'])
        println "assetClass = ${assetClass}"
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
        if (!assetClass) {
            println "no asset class found for create! ${params}"
        }
        assetClass = AssetClass.get(params['assetClass.id'])
        println("assetClass = ${assetClass.name}")
        asset.assetClass  = assetClass
        respond asset //, model:[assetClass: assetClass] 
    }

    @Secured(['ROLE_USER'])
    @Transactional
    def save(Asset assetInstance) {
        
        println('save params=' + params)
        if (assetInstance == null) {
            println('returning null')
            notFound()
            return
        }

        //TODO: we only want to create a Location when the 
        //asset is going to be saved
        if(!assetInstance.location) {
            assetInstance.location = new Location().save(flush: true)
            println "new location id = ${assetInstance.location.id}"            
        }
        
        
        assetInstance.validate()
        
        if (assetInstance.hasErrors()) {
            println("errors found ${assetInstance.getErrors()}")
            respond assetInstance.errors, view:'create'
            return
        }
    
        println(" assetClass id = " + params['assetClass.id'])
        
        assetInstance.save flush:true
        println("errors found post  save: ${assetInstance.getErrors()}")
       
//        def recentStatus = new Double(params['mostRecentStatus.status'])
//        println('creating a new status' + recentStatus)
//        def updateAdd = AssetStatus.findOrSaveWhere(asset: assetInstance,
//            statusDate: params['mostRecentStatus.statusDate'], status: recentStatus)
//        
//        assetInstance.mostRecentStatus = updateAdd
//
//        if (assetInstance.mostRecentStatus.hasErrors()) {
//            println("status errors ${assetInstance.mostRecentStatus.errors}")
//            //enterpriseInstance.errors << enterpriseInstance.location.address.errors
//            respond assetInstance.mostRecentStatus.errors, view: 'create'
//            return;
//        }
//        
        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assetInstance.label', default: 'Asset'), assetInstance.id])
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

        assetInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Asset.label', default: 'Asset'), assetInstance.id])
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

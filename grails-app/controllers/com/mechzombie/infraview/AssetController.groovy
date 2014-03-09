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
        params.max = Math.min(max ?: 10, 100)
        respond Asset.list(params), model:[assetInstanceCount: Asset.count()]
    }

    @Secured(['ROLE_USER'])
    def show(Asset assetInstance) {
        respond assetInstance
    }

    @Secured(['ROLE_USER'])
    def create(AssetClass assetClass) {
        //println('params for asset:' + params)
        //def assetClass = AssetClass.get(params['assetClass.id'])
       // println('ac id=' + assetClass.id)
        def asset = new Asset(params)
        //asset.assetClass = assetClass
        //println('asset class for asset = ' + asset.assetClass.id)
        respond asset, model:[assetClass: assetClass] 
    }

    @Secured(['ROLE_USER'])
    @Transactional
    def save(Asset assetInstance) {
        
        println('params=' + params)
        if (assetInstance == null) {
            println('returning null')
            notFound()
            return
        }

        if (assetInstance.hasErrors()) {
            println("errors found ${assetInstance.getErrors()}")
            respond assetInstance.errors, view:'create'
            return
        }

        assetInstance.save flush:true

       println("errors found post save: ${assetInstance.getErrors()}")
        
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

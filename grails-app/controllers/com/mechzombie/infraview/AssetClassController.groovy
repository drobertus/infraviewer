package com.mechzombie.infraview



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AssetClassController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        def theEnterprise = session['activeEnterprise']
        params.max = Math.min(max ?: 10, 100)
        respond AssetClass.list(params), model:[assetClassInstanceCount: AssetClass.count(), enterprise: theEnterprise]
    }

    def show(AssetClass assetClassInstance) {
        respond assetClassInstance
    }

    def create() {
        respond new AssetClass(params), model:[enterprise: session['activeEnterprise']]
    }

    @Transactional
    def save(AssetClass assetClassInstance) {
        if (assetClassInstance == null) {
            notFound()
            return
        }

        if (assetClassInstance.hasErrors()) {
            respond assetClassInstance.errors, view:'create'
            return
        }

        assetClassInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assetClassInstance.label', default: 'AssetClass'), assetClassInstance.id])
                redirect assetClassInstance
            }
            '*' { respond assetClassInstance, [status: CREATED] }
        }
    }

    def edit(AssetClass assetClassInstance) {
        respond assetClassInstance
    }

    @Transactional
    def update(AssetClass assetClassInstance) {
        if (assetClassInstance == null) {
            notFound()
            return
        }

        if (assetClassInstance.hasErrors()) {
            respond assetClassInstance.errors, view:'edit'
            return
        }

        assetClassInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'AssetClass.label', default: 'AssetClass'), assetClassInstance.id])
                redirect assetClassInstance
            }
            '*'{ respond assetClassInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(AssetClass assetClassInstance) {

        if (assetClassInstance == null) {
            notFound()
            return
        }

        assetClassInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'AssetClass.label', default: 'AssetClass'), assetClassInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'assetClassInstance.label', default: 'AssetClass'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

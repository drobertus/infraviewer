package com.mechzombie.infraview



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EnterpriseController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Enterprise.list(params), model:[enterpriseInstanceCount: Enterprise.count()]
    }

    def show(Enterprise enterpriseInstance) {
        respond enterpriseInstance
    }

    def create() {
        respond new Enterprise(params)
    }

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

    def edit(Enterprise enterpriseInstance) {
        respond enterpriseInstance
    }

    @Transactional
    def update(Enterprise enterpriseInstance) {
        if (enterpriseInstance == null) {
            notFound()
            return
        }

        if (enterpriseInstance.hasErrors()) {
            respond enterpriseInstance.errors, view:'edit'
            return
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

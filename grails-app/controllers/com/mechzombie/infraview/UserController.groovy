package com.mechzombie.infraview


import grails.plugins.springsecurity.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true )
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userInstanceCount: User.count()]
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN', 'ROLE_USER'])
    def show(User userInstance) {
        respond userInstance
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    def create() {
        respond new User(params)
    }
    
    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    @Transactional
    def save(User userInstance) {
        println 'saving user'
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'create'
            return
        }
        println('userroles=' + userInstance.authorities)
        
        
        userInstance.save flush:true
        println('params=' + params)
        UserRole.deleteByUser(userInstance)
         params.authorities.each() {
            println 'creating user role ' + it
            UserRole.create( userInstance, Role.getById(it), true)
            //UserRole.addToUserRoles(user: newUser, role: Role.getById(it))
        }
        
        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userInstance.label', default: 'User'), userInstance.username])
                redirect userInstance
            }
            '*' { respond userInstance, [status: CREATED] }
        }
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    def edit(User userInstance) {
        println 'editing user'
        respond userInstance 
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    @Transactional
    def update(User userInstance) {
        println 'updating user'
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'edit'
            return
        }
        
        println('userroles=' + userInstance.authorities)
        println('params=' + params)
        
        def existingRoles = userInstance.getAuthorities()
        def paramAuths = params.authorities
        paramAuths.each() {
            println 'creating user role ' + it
            def theRole = Role.findById(it)
            if(!existingRoles.contains(theRole)) {
                UserRole.create( userInstance, theRole, true)
            }
        }

        Role.list().each() { role->            
            def hasRole = existingRoles.contains(role)
            def auth = false
            if(hasRole){
                if(!paramAuths.contains(role.id.toString() ) ) {
                    UserRole.delete(userInstance, hasRole, true)
                }
            }
        }
        
        userInstance.save flush:true
        
        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), userInstance.username])
                redirect userInstance
            }
            '*'{ respond userInstance, [status: OK] }
        }
    }

    @Secured(['ROLE_SUPERUSER'])
    @Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }
        //TODO: change this from a delete to a 'deactivate' - have lists not return deactivated users
        userInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), userInstance.username])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userInstance.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

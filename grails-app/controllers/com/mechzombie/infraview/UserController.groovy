package com.mechzombie.infraview


import grails.plugins.springsecurity.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true )
class UserController {

    def springSecurityService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    def index(Integer max) {
        //println "params = " +params
        //println "principal =" + principal
        //def roleNames = principal.authorities*.authority
        // TODO:  if the user is a super user we display the list of users for
        // the enterprise that is "active"
        // def theEnterprise
        //if (roleNames.includes('ROLE_SUPERUSER')) {
         def theEnterprise = session['activeEnterprise']
        //}else {
          //  def user = User.findByUsername(principal.username)
        
          //  println "theUser= ${user.id}"
          //  theEnterprise = user.enterprise
       // }
        //params.max = Math.min(max ?: 10, 100)
        //def user = User.findByUsername(principal.username)
        
        //println "theUser= ${user.id}"
        //def theEnterprise = user.enterprise
        
        params.max = Math.min(max ?: 10, 100)
        def theUsers = User.findAllByEnterprise(theEnterprise, params)
        respond theUsers, model:[userInstanceCount: theUsers.size()]
        
        
        //respond User.list(params), model:[userInstanceCount: User.count()]
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    def enterpriseUsers(Integer max) {
        
        def roleNames = principal.authorities*.authority
        // TODO:  if the user is a super user we display the list of users for
        // the enterprise that is "active"
        def theEnterprise
        if (roleNames.includes('ROLE_SUPERUSER')) {
            theEnterprise = session['activeEnterprise']
        }else {
            def user = User.findByUsername(principal.username)
        
            println "theUser= ${user.id}"
            theEnterprise = user.enterprise
        }
        
        //params.max = Math.min(max ?: 10, 100)
        
        //def theEnterprise = user.enterprise
        
        params.max = Math.min(max ?: 10, 100)
        def theUsers = User.findAllByEnterprise(theEnterprise, params)
        respond theUsers, model:[userInstanceCount: theUsers.size()]
        
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
        
        if (userInstance == null) {      
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'create'
            return
        }
        
        userInstance.save flush:true
        
        def paramAuths = params.authorities
        paramAuths.each() {
            def theRole = Role.findById(it)
            UserRole.create( userInstance, theRole, true)
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
        respond userInstance 
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    @Transactional
    def update(User userInstance) {
        
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'edit'
            return
        }
        
        
        def existingRoles = userInstance.getAuthorities()       
        def paramAuths = params.authorities
        paramAuths.each() {
            def theRole = Role.findById(it)
            if(!existingRoles.contains(theRole)) {
              UserRole.create( userInstance, theRole, true)
            }
        }

        existingRoles.each() {
            if(!paramAuths.contains(it.id.toString() ) ) {
                UserRole.remove(userInstance, it, true)
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
        UserRole.removeAll(userInstance)
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

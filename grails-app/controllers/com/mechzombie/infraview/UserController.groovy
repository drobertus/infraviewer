package com.mechzombie.infraview


import grails.plugins.springsecurity.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true )
class UserController {

    def springSecurityService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    @Secured(['ROLE_USER'])
    def index(Integer max) {
        def theEnterprise = session['activeEnterprise']
        
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
        def theEnterprise = session.activeEnterprise
        //println 'create user params=' + params
        //println 'session variables=' + session
        def roleNames = springSecurityService.principal.authorities*.authority
        def roles = getAvailableRoles(theEnterprise, roleNames)
        //println "params =" + params
        render(view: 'create', model:[userInstance: new User(params), availableRoles: roles])

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
        def theEnterprise = session.activeEnterprise
        //println 'create user params=' + params
        //println 'session variables=' + session
        def roleNames = springSecurityService.principal.authorities*.authority
        def roles = getAvailableRoles(theEnterprise, roleNames)
        //println "params =" + params
        respond userInstance, model:[availableRoles: roles]
        //render(view: 'edit', model:[userInstance: userInstance, availableRoles: roles])
        
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
        //UserRole.removeAll(userInstance)
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
    
    
    private List<Role> getAvailableRoles(Enterprise enterprise, List<String> userAuths) {
        def roleList = []
        //println("user auths = ${userAuths}")
        if ('InfraView' != enterprise?.name || !userAuths.contains('ROLE_SUPERUSER')) {
            Role.findAllByAuthorityNotEqual('ROLE_SUPERUSER').each() {
                roleList << it
            } 
        } 
        else {
            Role.findAll().each() {
                roleList << it
            }
        }
//        roleList.each() {
//            println 'role=' + it.authority
//        }
        return roleList
        
    }
}

package com.mechzombie.infraview

import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true )
class InfraUserController {

    def springSecurityService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    @Secured(['ROLE_USER'])
    def index(Integer max) {
        println "params- userCOntroller.index=${params}"
        def theEnterprise = session['activeEnterprise']
        
        params.max = Math.min(max ?: 10, 100)
        def theUsers = InfraUser.findAllByEnterprise(theEnterprise, params)
        println "userCount=${theUsers.size()}"
        respond theUsers, model:[infraUserInstanceCount: theUsers.size()]
    }
    
    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN', 'ROLE_USER'])
    def show(InfraUser infraUserInstance) {
        println "userInstance- userCOntroller.show=${infraUserInstance}"
        respond infraUserInstance
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    def create() {
        def theEnterprise = session.activeEnterprise
        //println 'create user params=' + params
        //println 'session variables=' + session
        def roleNames = springSecurityService.principal.authorities*.authority
        def roles = getAvailableRoles(theEnterprise, roleNames)
        //println "params =" + params
        render(view: 'create', model:[infraUserInstance: new InfraUser(params), availableRoles: roles])

    }
    
    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    @Transactional
    def save(InfraUser infraUserInstance) {
        
        if ( infraUserInstance == null) {      
            notFound()
            return
        }

        if ( infraUserInstance.hasErrors()) {
            println 'user instance has errors! can not save-' + infraUserInstance.errors
            respond infraUserInstance.errors, view:'create'
            return
        }
        
        infraUserInstance.save flush:true
        
        def paramAuths = params.authorities
        paramAuths.each() {
            def theRole = Role.findById(it)
            InfraUserRole.create( infraUserInstance, theRole, true)
        }
      
        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'infraUserInstance.label', default: 'User'), infraUserInstance.username])
                redirect infraUserInstance
            }
            '*' { respond infraUserInstance, [status: CREATED] }
        }        
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    def edit(InfraUser infraUserInstance) {
        println 'editing user params:' + params
        def theEnterprise = session.activeEnterprise
        //println 'create user params=' + params
        //println 'session variables=' + session
        def roleNames = springSecurityService.principal.authorities*.authority
        def roles = getAvailableRoles(theEnterprise, roleNames)
        //println "params =" + params
        respond infraUserInstance, model:[availableRoles: roles]
        //render(view: 'edit', model:[userInstance: infraUserInstance, availableRoles: roles])
        
    }

    @Secured(['ROLE_SUPERUSER', 'ROLE_ADMIN'])
    @Transactional
    def update(InfraUser infraUserInstance) {
        
        if ( infraUserInstance == null) {
            notFound()
            return
        }

        if ( infraUserInstance.hasErrors()) {
            println "update failed- user has errors: ${infraUserInstance.errors}"
            respond infraUserInstance.errors, view:'edit'
            return
        }
        
        
        def existingRoles = infraUserInstance.getAuthorities()       
        def paramAuths = params.authorities
        paramAuths.each() {
            def theRole = Role.findById(it)
            if(!existingRoles.contains(theRole)) {
              InfraUserRole.create( infraUserInstance, theRole, true)
            }
        }

        existingRoles.each() {
            if(!paramAuths.contains(it.id.toString() ) ) {
                InfraUserRole.remove(infraUserInstance, it, true)
            }
        }
        
        infraUserInstance.save flush:true
        
        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'InfraUser.label', default: 'User'), infraUserInstance.username])
                redirect infraUserInstance
            }
            '*'{ respond infraUserInstance, [status: OK] }
        }
    }

    @Secured(['ROLE_SUPERUSER'])
    @Transactional
    def delete(InfraUser infraUserInstance) {

        if ( infraUserInstance == null) {
            notFound()
            return
        }
        //TODO: change this from a delete to a 'deactivate' - have lists not return deactivated users
        //UserRole.removeAll(userInstance)
        infraUserInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'InfraUser.label', default: 'User'), infraUserInstance.username])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'infraUserInstance.label', default: 'User'), params.id])
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

package com.mechzombie.infraview

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class HomeController {
    def springSecurityService

    def index() {
        //def roles = springSecurityService.getPrincipal().getAuthorities()
        // will be a List of String
        def roleNames = principal.authorities*.authority
        println 'roles size=' + roleNames.size
        roleNames.each() {
            println it
        }

        if (roleNames.contains('ROLE_SUPERUSER')) {
            redirect controller: 'sysadmin', params: params
            return
        }
        else if (roleNames.contains('ROLE_ADMIN')) {
            redirect action: 'adminOnly'
            return
        }

    }

    @Secured(['ROLE_ADMIN'])
    def adminOnly() {
        
        println 'admin only redirect'
        def theAdmin = User.findByUsername(principal.username)
       //model:[enterpriseInstanceCount: Enterprise.count()
       println "enterprise = ${theAdmin.enterprise.name}"
        //params[enterpriseInstance] = theAdmin.enterprise
        //respond Enterprise.list(params), model:[enterpriseInstanceCount: Enterprise.count()]
        redirect theAdmin.enterprise //controller: 'enterprise', action: 'show', 
        return
    }
}

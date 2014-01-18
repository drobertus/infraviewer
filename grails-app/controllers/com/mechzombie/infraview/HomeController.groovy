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

    }

    @Secured(['ROLE_ADMIN'])
    def adminOnly() {
        render 'admin only stuff'
    }
}

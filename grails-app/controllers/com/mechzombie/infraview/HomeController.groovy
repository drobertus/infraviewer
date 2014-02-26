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
        else { 
            def theUser = User.findByUsername(principal.username)
            session['activeEnterprise'] = theUser.enterprise
            if (roleNames.contains('ROLE_ADMIN')) {
                
                //redirect action: 'adminOnly'
                render(view: "adminOnly", model: [user: theUser, isAdmin:true, enterprise: theUser.enterprise])
                return
            }
            else
            {
                //def theUser = User.findByUsername(principal.username)
                respond view:'user_index' , model:[user: theUser, isAdmin: false, enterprise: theUser.enterprise]
                return
            }
        }

    }

//    @Secured(['ROLE_ADMIN'])
//    def adminOnly() {
//        
//        def theUser = User.findByUsername(principal.username)
//        session['activeEnterprise'] = theUser.enterprise
//            
//        println 'admin only redirect'
//        // model:[enterpriseInstanceCount: Enterprise.count()]
//        println "enterprise = ${theUser.enterprise.name}"
//        //params[enterpriseInstance] = theAdmin.enterprise
//        //respond model:[user: thUser, isAdmin:true, enterprise: theUser.enterprise]
//        render(view: "adminOnly", model: [user: theUser, isAdmin:true, enterprise: theUser.enterprise])
//    }
}

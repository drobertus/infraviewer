package com.mechzombie.infraview

import grails.plugin.springsecurity.annotation.Secured
import grails.converters.JSON

@Secured(['ROLE_SUPERUSER'])
class SysadminController {

    def index() {
        println('sysadmin controller')
        def allEnts = Enterprise.getAll()

        render(view: "index", model: [allEnts: allEnts, entityName: 'Enterprise'])
    }

    def getListOfEnterprises() {
        render Enterprise.listOrderByName() as JSON        
    }
}

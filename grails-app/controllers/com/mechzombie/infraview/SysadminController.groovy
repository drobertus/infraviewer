package com.mechzombie.infraview

import grails.plugin.springsecurity.annotation.Secured
import grails.converters.JSON

@Secured(['ROLE_SUPERUSER'])
class SysadminController {

    def index() {
        def allEnts = Enterprise.getAll()

        render(view: "index", model: [allEnts: allEnts, entityName: 'Enterprise'])
    }

    def getListOfEnterprises() {
        def ents = Enterprise.getAll()
        render ents as JSON
    }
}

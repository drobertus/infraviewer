package com.mechzombie.infraview

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

@Secured(['ROLE_SUPERUSER'])
class SysadminController {

    def index() {
        def allEnts = Enterprise.getAll()

        render(view: "index", model: [allEnts: allEnts])
    }

    def getListOfEnterprises() {
        def ents = Enterprise.getAll()
        render ents as JSON
    }
}

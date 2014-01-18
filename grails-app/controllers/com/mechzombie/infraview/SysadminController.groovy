package com.mechzombie.infraview

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_SUPERUSER'])
class SysadminController {

    def index() {  }
}

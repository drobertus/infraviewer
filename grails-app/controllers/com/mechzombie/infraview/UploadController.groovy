package com.mechzombie.infraview

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class UploadController {

    def fileService
    
    def uploadEnterpriseLogo() {
        println "trying to upload image: ${params}"
        def theEnt = session['activeEnterprise']
              
        def f = request.getFile('logoImage')
        fileService.uploadEnterpriseLogo(f.bytes, "blah", theEnt)    
 
        forward controller: "enterprise", action: "edit", params: [id: theEnt.id]
        
    }
}

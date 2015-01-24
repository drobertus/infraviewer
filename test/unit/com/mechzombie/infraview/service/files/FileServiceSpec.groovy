package com.mechzombie.infraview.service.files

import grails.test.mixin.TestFor
import groovy.util.logging.Log
import spock.lang.Specification
import com.mechzombie.infraview.filedata.EnterpriseFolder

import static org.junit.Assert.assertArrayEquals

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Log
@TestFor(FileService)
class FileServiceSpec extends Specification {

    static transactional = false

    def entId = 41l
    def testFolder
    
    void "test Enterprise logo file handling"() {
        setup:
            def theTestLogo = new byte[100]
            100.times {
                theTestLogo[it] = (byte)'0'
            }
            
        when:        
            service.uploadEnterpriseLogo(theTestLogo, "testImage.jpg", entId)           
        then:
            assertEquals 1, service.enterpriseFolders.size()         
        when:
            service.enterpriseFolders.values().each() { it ->
                log.info "found id ${it.enterpriseId}"
                
                log.info "logoPath= ${it.logo.absolutePath}"
            }
        
            def folder = service.enterpriseFolders[entId]
            testFolder = folder.mainFolder
        then:
            assertNotNull folder
            assertEquals entId, folder.enterpriseId

        when:
         def theImage = service.getCurrentEnterpriseLogo(entId)
         
        then:
            assertArrayEquals theTestLogo, theImage
    }
    
    def setup() {

        
    }

    def cleanup() {
        if (testFolder) {
            testFolder.deleteDir()  
        }
    }

    void "test something"() {
    }
}

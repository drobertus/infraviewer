/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview

import spock.lang.Specification
/**
 *
 * @author David
 */
class ReportTypeTest extends Specification{
	
    def setup() {
        
    }
    
    def cleanup() {
        
    }
    
    def "test that the enum works as hoped"() {
        when:
            def fullTitleVal = ReportType.AssetReplacementProjection.getFullTitle()
        then:
            assertEquals("Asset Replacement Projection", fullTitleVal)
        
    }
    
}


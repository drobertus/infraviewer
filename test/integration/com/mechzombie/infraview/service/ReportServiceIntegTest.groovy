/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview.service

import com.mechzombie.infraview.*
import spock.lang.*
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

/**
 *
 * @author David
 */
class ReportServiceIntegTest extends Specification {
	
    def grailsApplication
    def rptsToRun = [:]
    
    def testEnt
    def assetClasses = []
    
    def ReportService reportService = new ReportService()
    
    void "test Asset Replacement Projection report"() {
        when:
            def replacementRpt = rptsToRun.get(ReportType.AssetReplacementProjection)
            def reportName = reportService.runReport(replacementRpt)
            
        
        then:
            
            //assertNotNull testEnt
            //assert (assetClasses.size > 0)
            println "testEnt= ${testEnt.name}"
            println "assetclass count= ${assetClasses.size}"
            assertNotNull reportName
            
    }
    
    void "test Asset Replacement History report"() {
        
    }
    
    void "test Inspection and Maintenance Projection report"() {
        
    }
    
    void "test Inspection and Maintenance History report"() {
        
    }
    //TODO test each type of report
    
    def setup() {        
        
        File testRptFolder = new File(grailsApplication.config.infraview.report.root_folder )
        log.info("report folder name = ${testRptFolder.absolutePath}")
        testRptFolder.mkdirs()
        
        //TODO: add a report of each type  
        //first we need an enterprise
        def activeDt = new Date()
        testEnt = new Enterprise(name: 'ReportTEstEnt', activeDate: activeDt, location: new Location().save()).save(flush: true)
      
        def posts = new AssetClass(name:'Sign post', enterprise: testEnt, 
            description: 'metal post',
            statusValueNew: 10.0,
            statusValueReplace: 5.0,
            statusValueDestroyed: 0.0,
            expectedLifeSpanYears: 10.0,
            standardInspectionInterval: 2.0,
            standardMaintenanceInterval: 5.0,
            assetHasAddress: false,
            assetHasLocation: true,
            assetGeometryType: Geometry.GeometryType.POINT,
            organic: false,
            assetClassCategory: "posts").save(flush: true)  
        
        def hydrants = new AssetClass(name:'Hydrant', 
            enterprise: testEnt, 
            description: 'hydrant',
            statusValueNew: 10.0,
            statusValueReplace: 7.0,
            statusValueDestroyed: 0.0,
            expectedLifeSpanYears: 40.0,
            standardInspectionInterval: 2.0,
            standardMaintenanceInterval: 5.0,
            assetHasAddress: false,
            assetHasLocation: true,
            assetGeometryType: Geometry.GeometryType.POINT,
            organic: false,
            assetClassCategory: "trees").save(flush: true)
        
        assertNotNull testEnt
        //then we need multiple asset classes
        assetClasses << testEnt.assetClasses
      
        assert assetClasses.size > 0
      
        //now add a few more
      
        
        //and individual assets
        //each with a history
      
        //then reports of each type
      
        def startDate = new Date()
        def endDate = startDate.plus(365 * 25)
        def hydReplaceRpt = new Report(enterprise: testEnt, title: "Hydrant replacement Projection",
            reportType: ReportType.AssetReplacementProjection ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate).save(flush: true)
    
        def inspMaintRpt = new Report(enterprise: testEnt, title: "Hydrant Maintenance Projection",
            reportType: ReportType.InspectionAndMaintenanceProjection ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate).save(flush: true)
    
        
        rptsToRun.put (ReportType.AssetReplacementProjection, hydReplaceRpt)
        rptsToRun.put (ReportType.InspectionAndMaintenanceProjection, inspMaintRpt)
            // AssetReplacementProjection("Asset Replacement Projection"),
            // 
        endDate = startDate.minus(365 * 25)
        def hydReplaceHist = new Report(enterprise: testEnt, title: "Hydrant replacement History",
            reportType: ReportType.AssetReplacementHistory ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate).save(flush: true)
      
        def inspMainHist = new Report(enterprise: testEnt, title: "Hydrant maintenance History",
            reportType: ReportType.InspectionAndMaintenanceHistory ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate).save(flush: true)
        
        rptsToRun.put (ReportType.AssetReplacementHistory, hydReplaceHist)   
        rptsToRun.put (ReportType.InspectionAndMaintenanceHistory, inspMainHist)   

        
        def hydProjRptOverdue = new Report(enterprise: testEnt, title: "Hydrant replacement overdue",
            reportType: ReportType.AssetReplacementOverdue ,
            startDate: startDate,
            endDate: endDate , 
            scheduledRunDate: startDate).save(flush: true)
      
        def inspMaintOverdue = new Report(enterprise: testEnt, title: "Hydrant maintenance overdue",
            reportType: ReportType.InspectionAndMaintenanceOverdue ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate).save(flush: true)
        
        rptsToRun.put (ReportType.AssetReplacementOverdue, hydProjRptOverdue)   
        rptsToRun.put (ReportType.InspectionAndMaintenanceOverdue, inspMaintOverdue)   

        
        
        //InspectionAndMaintenanceOverdue("Current Inspection and Maintenance Overdue"),
        //AssetReplacementOverdue("Current Asset Replacement Overdue"),
        assert rptsToRun.size() == 6
    }
    
    def cleanup() {
        
        //delete the report files that were generated
    }
    
}

